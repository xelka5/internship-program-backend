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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.tusofia.internshipprogram.util.GlobalConstants.*;

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
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

    InternReport report = reportRepository
            .findByTrackingNumber(reportTrackingNumber)
            .orElseThrow(() -> new EntityNotFoundException(REPORT_NOT_FOUND_MESSAGE));

    if (!report.getInternDetails().getUser().getEmail().equals(savedUser.getEmail())) {
      throw new InsufficientRightsException(INSUFFICIENT_RIGHTS_REPORT_EDIT_MESSAGE);
    }

    return reportMapper.internReportToInternReportDto(report);
  }

  @Override
  public BaseResponseDto createReport(InternReportDto internReportDto, String userEmail) {
    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

    Internship internship = internshipRepository
            .findByTrackingNumber(internReportDto.getInternshipTrackingNumber())
            .orElseThrow(() -> new EntityNotFoundException(INTERNSHIP_NOT_FOUND_MESSAGE));

    InternReport internReport = reportMapper.internReportDtoUserAndInternshipToInternReport(internReportDto, savedUser, internship);
    reportRepository.save(internReport);

    return new BaseResponseDto(true);
  }

  @Override
  public List<InternReportDto> getInternReportsByInternEmail(String internshipTrackingNumber, String internEmail) {
    User intern = userRepository.findByEmail(internEmail)
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

    InternDetails internDetails = intern.getInternDetails();

    List<InternReport> reports = internDetails.getInternReports()
            .stream()
            .filter(report -> report.getInternship().getTrackingNumber().equals(internshipTrackingNumber))
            .collect(Collectors.toList());

    return reportMapper.internReportListToInternReportDtoList(reports);
  }


  @Override
  public BaseResponseDto editReport(InternReportDto updatedReport, String userEmail, String reportTrackingNumber) {
    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

    InternReport report = reportRepository
            .findByTrackingNumber(reportTrackingNumber)
            .orElseThrow(() -> new EntityNotFoundException(REPORT_NOT_FOUND_MESSAGE));

    if (!report.getInternDetails().getUser().getEmail().equals(savedUser.getEmail())) {
      throw new InsufficientRightsException(INSUFFICIENT_RIGHTS_REPORT_EDIT_MESSAGE);
    }

    reportMapper.updateReport(updatedReport, report);
    reportRepository.save(report);

    return new BaseResponseDto(true);
  }

  @Override
  public BaseResponseDto deleteReport(String reportTrackingNumber, String userEmail) {
    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

    InternReport report = reportRepository
            .findByTrackingNumber(reportTrackingNumber)
            .orElseThrow(() -> new EntityNotFoundException(REPORT_NOT_FOUND_MESSAGE));

    if (!report.getInternDetails().getUser().getEmail().equals(savedUser.getEmail())) {
      throw new InsufficientRightsException(INSUFFICIENT_RIGHTS_REPORT_EDIT_MESSAGE);
    }

    reportRepository.delete(report);

    return new BaseResponseDto(true);
  }

}
