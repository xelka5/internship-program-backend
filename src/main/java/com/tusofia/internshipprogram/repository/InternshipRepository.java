package com.tusofia.internshipprogram.repository;

import com.tusofia.internshipprogram.entity.internship.Internship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InternshipRepository extends JpaRepository<Internship, Long> {

  Optional<Internship> findByTrackingNumber(String trackingNumber);

}
