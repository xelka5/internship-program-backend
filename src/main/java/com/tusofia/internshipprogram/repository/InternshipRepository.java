package com.tusofia.internshipprogram.repository;

import com.tusofia.internshipprogram.entity.internship.Internship;
import com.tusofia.internshipprogram.enumeration.InternshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InternshipRepository extends JpaRepository<Internship, Long> {

  Optional<Internship> findByTrackingNumber(String trackingNumber);

  List<Internship> findByStatusOrderByCreateDateDesc(InternshipStatus status);

}
