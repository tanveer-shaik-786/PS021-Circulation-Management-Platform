package com.bibliotech.fine_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bibliotech.fine_service.model.Fine;

public interface FineRepository extends JpaRepository<Fine, Integer> {

}