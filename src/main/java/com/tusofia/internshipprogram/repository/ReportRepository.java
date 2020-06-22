package com.tusofia.internshipprogram.repository;

import com.tusofia.internshipprogram.entity.report.InternReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<InternReport, Long> {

  Optional<InternReport> findByTrackingNumber(String trackingNumber);
}
