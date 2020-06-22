package com.tusofia.internshipprogram.service.impl;

import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.dto.report.InternReportDto;
import com.tusofia.internshipprogram.entity.internship.Internship;
import com.tusofia.internshipprogram.entity.report.InternReport;
import com.tusofia.internshipprogram.entity.user.InternDetails;
import com.tusofia.internshipprogram.entity.user.User;
import com.tusofia.internshipprogram.exception.EntityNotFoundException;
import com.tusofia.internshipprogram.exception.InsufficientRightsException;
import com.tusofia.internshipprogram.mapper.ReportMapper;
import com.tusofia.internshipprogram.repository.InternshipRepository;
import com.tusofia.internshipprogram.repository.ReportRepository;
import com.tusofia.internshipprogram.repository.UserRepository;
import com.tusofia.internshipprogram.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

  private ReportMapper reportMapper;
  private UserRepository userRepository;
  private ReportRepository reportRepository;
  private InternshipRepository internshipRepository;

  public ReportServiceImpl(ReportMapper reportMapper, UserRepository userRepository,
                           InternshipRepository internshipRepository, ReportRepository reportRepository) {
    this.reportMapper = reportMapper;
    this.userRepository = userRepository;
    this.reportRepository = reportRepository;
    this.internshipRepository = internshipRepository;
  }

  @Override
  public InternReportDto getInternReport(String reportTrackingNumber, String userEmail) {
    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException("User does not exist"));

    InternReport report = reportRepository
            .findByTrackingNumber(reportTrackingNumber)
            .orElseThrow(() -> new EntityNotFoundException("Report does not exist"));

    if(!report.getInternDetails().getUser().getEmail().equals(savedUser.getEmail())) {
      throw new InsufficientRightsException("User has no rights to edit report");
    }

    return reportMapper.internReportToInternReportDto(report);
  }

  @Override
  public BaseResponseDto createReport(InternReportDto internReportDto, String userEmail) {
    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException("User does not exist"));

    Internship internship = internshipRepository
            .findByTrackingNumber(internReportDto.getInternshipTrackingNumber())
            .orElseThrow(() -> new EntityNotFoundException("Internship does not exist"));

    InternReport internReport = reportMapper.internReportDtoUserAndInternshipToInternReport(internReportDto, savedUser, internship);
    reportRepository.save(internReport);

    return new BaseResponseDto(true);
  }

  @Override
  public List<InternReportDto> getInternReportsForInternship(String internshipTrackingNumber, String userEmail) {
    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException("User does not exist"));

    InternDetails internDetails = savedUser.getInternDetails();

    List<InternReport> reports = internDetails.getInternReports()
            .stream()
            .filter(report -> report.getInternship().getTrackingNumber().equals(internshipTrackingNumber))
            .collect(Collectors.toList());

    return reportMapper.internReportListToInternReportDtoList(reports);
  }



  @Override
  public BaseResponseDto editReport(InternReportDto updatedReport, String userEmail, String reportTrackingNumber) {
    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException("User does not exist"));

    InternReport report = reportRepository
            .findByTrackingNumber(reportTrackingNumber)
            .orElseThrow(() -> new EntityNotFoundException("Report does not exist"));

    if(!report.getInternDetails().getUser().getEmail().equals(savedUser.getEmail())) {
      throw new InsufficientRightsException("User has no rights to edit report");
    }

    reportMapper.updateReport(updatedReport, report);
    reportRepository.save(report);

    return new BaseResponseDto(true);
  }

  @Override
  public BaseResponseDto deleteReport(String reportTrackingNumber, String userEmail) {
    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException("User does not exist"));

    InternReport report = reportRepository
            .findByTrackingNumber(reportTrackingNumber)
            .orElseThrow(() -> new EntityNotFoundException("Report does not exist"));

    if(!report.getInternDetails().getUser().getEmail().equals(savedUser.getEmail())) {
      throw new InsufficientRightsException("User has no rights to edit report");
    }

    reportRepository.delete(report);

    return new BaseResponseDto(true);
  }

}
