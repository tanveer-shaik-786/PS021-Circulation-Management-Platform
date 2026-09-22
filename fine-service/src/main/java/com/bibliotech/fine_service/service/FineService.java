package com.bibliotech.fine_service.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bibliotech.fine_service.model.Fine;
import com.bibliotech.fine_service.repository.FineRepository;

@Service
public class FineService {

    @Autowired
    FineRepository fr;

    public Fine addFine(Fine fine) {
        return fr.save(fine);
    }

    public List<Fine> getAllFines() {
        return fr.findAll();
    }

    public Fine getFineById(int id) {
        return fr.findById(id).orElse(null);
    }

    public Fine updateFine(int id, Fine fine) {

        Fine existingFine = fr.findById(id).orElse(null);

        if (existingFine == null) {
            return null;
        }

        existingFine.setRentalId(fine.getRentalId());
        existingFine.setAmount(fine.getAmount());
        existingFine.setStatus(fine.getStatus());

        return fr.save(existingFine);
    }

    public void deleteFine(int id) {
        fr.deleteById(id);
    }

    public Fine calculateFine(int rentalId, LocalDate issueDate, LocalDate returnDate) {

        LocalDate dueDate = issueDate.plusDays(7);

        long overdueDays = 0;

        if (returnDate.isAfter(dueDate)) {
            overdueDays = ChronoUnit.DAYS.between(dueDate, returnDate);
        }

        double amount = overdueDays * 10;

        String status;

        if (amount == 0) {
            status = "PAID";
        } else {
            status = "UNPAID";
        }

        Fine fine = new Fine(rentalId, amount, status);

        return fr.save(fine);
    }
}