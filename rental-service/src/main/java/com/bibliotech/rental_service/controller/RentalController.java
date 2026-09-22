package com.bibliotech.rental_service.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.bibliotech.rental_service.model.Rental;
import com.bibliotech.rental_service.service.RentalService;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    @Autowired
    RentalService rs;

    @PostMapping("/add")
    public Rental addRental(@RequestBody Rental rental) {
        return rs.addRental(rental);
    }

    @GetMapping("/show")
    public List<Rental> getAllRentals() {
        return rs.getAllRentals();
    }

    @GetMapping("/showby/{id}")
    public Rental getRentalById(@PathVariable int id) {
        return rs.getRentalById(id);
    }

    @PutMapping("/updateby/{id}")
    public Rental updateRental(@PathVariable int id, @RequestBody Rental rental) {
        return rs.updateRental(id, rental);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteRental(@PathVariable int id) {
        rs.deleteRental(id);
        return "Rental deleted successfully";
    }
    
    @PutMapping("/return/{id}")
    public Rental returnRental(@PathVariable int id) {
        return rs.returnRental(id);
    }
}