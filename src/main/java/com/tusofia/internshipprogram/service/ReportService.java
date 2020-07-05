package com.tusofia.internshipprogram.service;

import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.dto.report.InternReportDto;

import java.util.List;

public interface ReportService {

  InternReportDto getInternReport(String reportTrackingNumber, String userEmail);

  BaseResponseDto createReport(InternReportDto internReport, String userEmail);

  List<InternReportDto> getInternReportsByInternEmail(String internshipTrackingNumber, String userEmail);

  BaseResponseDto editReport(InternReportDto internReport, String userEmail, String reportTrackingNumber);

  BaseResponseDto deleteReport(String reportTrackingNumber, String userEmail);

}
