package com.bibliotech.fine_service.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.bibliotech.fine_service.model.Fine;
import com.bibliotech.fine_service.service.FineService;

@RestController
@RequestMapping("/fines")
public class FineController {

    @Autowired
    FineService fs;

    @PostMapping("/add")
    public Fine addFine(@RequestBody Fine fine) {
        return fs.addFine(fine);
    }

    @GetMapping("/show")
    public List<Fine> getAllFines() {
        return fs.getAllFines();
    }

    @GetMapping("/showby/{id}")
    public Fine getFineById(@PathVariable int id) {
        return fs.getFineById(id);
    }

    @PutMapping("/updateby/{id}")
    public Fine updateFine(@PathVariable int id, @RequestBody Fine fine) {
        return fs.updateFine(id, fine);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteFine(@PathVariable int id) {
        fs.deleteFine(id);
        return "Fine deleted successfully";
    }
    
    
    
    
    @PostMapping("/calculate/{rentalId}")
    public Fine calculateFine(
            @PathVariable int rentalId,
            @RequestParam LocalDate issueDate,
            @RequestParam LocalDate returnDate) {

        return fs.calculateFine(
                rentalId,
                issueDate,
                returnDate);
    }
}