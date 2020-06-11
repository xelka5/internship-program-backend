package com.tusofia.internshipprogram.repository;

import com.tusofia.internshipprogram.entity.application.InternshipApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<InternshipApplication, Long> {
}
