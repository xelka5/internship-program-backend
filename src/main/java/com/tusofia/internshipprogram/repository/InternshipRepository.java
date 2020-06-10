package com.tusofia.internshipprogram.repository;

import com.tusofia.internshipprogram.entity.internship.Internship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternshipRepository extends JpaRepository<Internship, Long> {

}
