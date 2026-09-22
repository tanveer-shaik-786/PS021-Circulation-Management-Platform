package com.bibliotech.rental_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bibliotech.rental_service.model.Rental;

public interface RentalRepository extends JpaRepository<Rental, Integer> {
    Rental findByUserIdAndBookIdAndReturnDateIsNull(
            int userId, int bookId);
}