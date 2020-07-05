package com.tusofia.internshipprogram.service;

import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.dto.finalReport.CreateFinalReportRequestDto;
import com.tusofia.internshipprogram.dto.finalReport.CreateFinalReportResponseDto;
import com.tusofia.internshipprogram.dto.finalReport.FinalReportWithInternProfileDto;
import com.tusofia.internshipprogram.dto.finalReport.FinalReportEmployerDto;
import org.springframework.web.multipart.MultipartFile;

public interface FinalReportService {

  FinalReportEmployerDto getInternshipReportInfoEmployer(String internshipTrackingNumber, String userEmail);

  CreateFinalReportResponseDto createFinalReportEmployer(CreateFinalReportRequestDto createFinalReportRequestDto, String userEmail);

  BaseResponseDto uploadFinalReport(MultipartFile finalReportFile, String reportTrackingNumber);

  CreateFinalReportResponseDto createFinalReportIntern(CreateFinalReportRequestDto createFinalReportRequestDto, String userEmail);

  FinalReportWithInternProfileDto getFinalReportByInternshipAndUser(String internshipTrackingNumber, String userEmail);
}
