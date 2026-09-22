package com.bibliotech.rental_service.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.bibliotech.rental_service.model.Rental;
import com.bibliotech.rental_service.repository.RentalRepository;

@Service
public class RentalService {

    @Autowired
    RentalRepository rr;

    @Autowired
    RestTemplate rt;
    
    public Rental addRental(Rental rental) {

        Rental existingRental =
                rr.findByUserIdAndBookIdAndReturnDateIsNull(
                        rental.getUserId(),
                        rental.getBookId());

        if (existingRental != null) {
            return null;
        }

        String url = "http://BOOK-SERVICE/books/showby/"
                + rental.getBookId();

        Map<String, Object> book =
                rt.getForObject(url, Map.class);

        if (book == null) {
            return null;
        }

        int availableCopies =
                (Integer) book.get("availableCopies");

        if (availableCopies <= 0) {
            return null;
        }

        String borrowUrl =
                "http://BOOK-SERVICE/books/borrow/"
                + rental.getBookId();

        rt.put(borrowUrl, null);

        return rr.save(rental);
    }

    public Rental returnRental(int id) {

        Rental rental = rr.findById(id).orElse(null);

        if (rental == null) {
            return null;
        }

        String bookUrl = "http://BOOK-SERVICE/books/return/"
                + rental.getBookId();

        rt.put(bookUrl, null);

        rental.setReturnDate(java.time.LocalDate.now());

        String fineUrl = "http://FINE-SERVICE/fines/calculate/"
                + rental.getRentalId()
                + "?issueDate=" + rental.getIssueDate()
                + "&returnDate=" + rental.getReturnDate();

        rt.postForObject(fineUrl, null, Map.class);

        return rr.save(rental);
    }

    public List<Rental> getAllRentals() {
        return rr.findAll();
    }

    public Rental getRentalById(int id) {
        return rr.findById(id).orElse(null);
    }

    public Rental updateRental(int id, Rental rental) {

        Rental existingRental = rr.findById(id).orElse(null);

        if (existingRental == null) {
            return null;
        }

        existingRental.setUserId(rental.getUserId());
        existingRental.setBookId(rental.getBookId());
        existingRental.setIssueDate(rental.getIssueDate());
        existingRental.setReturnDate(rental.getReturnDate());

        return rr.save(existingRental);
    }

    public void deleteRental(int id) {
        rr.deleteById(id);
    }
}