package com.tusofia.internshipprogram.repository;

import com.tusofia.internshipprogram.entity.finalReport.FinalReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FinalReportRepository extends JpaRepository<FinalReport, Long> {

  Optional<FinalReport> findByTrackingNumber(String trackingNumber);
}
