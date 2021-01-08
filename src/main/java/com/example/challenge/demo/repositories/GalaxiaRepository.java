package com.example.challenge.demo.repositories;

import com.example.challenge.demo.entities.Galaxia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GalaxiaRepository extends JpaRepository<Galaxia, Long> {
}
