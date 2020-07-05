package com.tusofia.internshipprogram.service.impl;

import com.tusofia.internshipprogram.config.ApplicationConfig;
import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.dto.finalReport.CreateFinalReportRequestDto;
import com.tusofia.internshipprogram.dto.finalReport.CreateFinalReportResponseDto;
import com.tusofia.internshipprogram.dto.finalReport.FinalReportWithInternProfileDto;
import com.tusofia.internshipprogram.dto.finalReport.FinalReportEmployerDto;
import com.tusofia.internshipprogram.entity.application.InternshipApplication;
import com.tusofia.internshipprogram.entity.finalReport.FinalReport;
import com.tusofia.internshipprogram.entity.internship.Internship;
import com.tusofia.internshipprogram.enumeration.ApplicationStatus;
import com.tusofia.internshipprogram.enumeration.FinalReportType;
import com.tusofia.internshipprogram.exception.EntityNotFoundException;
import com.tusofia.internshipprogram.exception.InsufficientRightsException;
import com.tusofia.internshipprogram.mapper.FinalReportMapper;
import com.tusofia.internshipprogram.repository.ApplicationRepository;
import com.tusofia.internshipprogram.repository.FinalReportRepository;
import com.tusofia.internshipprogram.repository.InternshipRepository;
import com.tusofia.internshipprogram.service.FinalReportService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FinalReportServiceImpl implements FinalReportService {

  private InternshipRepository internshipRepository;
  private FinalReportRepository finalReportRepository;
  private ApplicationRepository applicationRepository;
  private FinalReportMapper finalReportMapper;
  private ApplicationConfig applicationConfig;

  public FinalReportServiceImpl(InternshipRepository internshipRepository, FinalReportRepository finalReportRepository,
                                ApplicationRepository applicationRepository, FinalReportMapper finalReportMapper, ApplicationConfig applicationConfig) {
    this.internshipRepository = internshipRepository;
    this.finalReportRepository = finalReportRepository;
    this.applicationRepository = applicationRepository;
    this.finalReportMapper = finalReportMapper;
    this.applicationConfig = applicationConfig;
  }

  @Override
  public FinalReportEmployerDto getInternshipReportInfoEmployer(String internshipTrackingNumber, String userEmail) {
    Internship savedInternship = internshipRepository
            .findByTrackingNumber(internshipTrackingNumber)
            .orElseThrow(() -> new EntityNotFoundException("Internship does not exist"));

    if(!savedInternship.getEmployer().getUser().getEmail().equals(userEmail)) {
      throw new InsufficientRightsException("User does not have rights to get final reports");
    }

    List<InternshipApplication> acceptedInterns = savedInternship.getApplications()
            .stream()
            .filter(application -> application.getStatus() == ApplicationStatus.ACCEPTED)
            .collect(Collectors.toList());

    List<FinalReportWithInternProfileDto> finalReports = finalReportMapper.internshipApplicationListToFinalReportDtoList(acceptedInterns);

    return finalReportMapper.internshipAndReportListToFinalReportEmployerDto(savedInternship, finalReports);
  }

  @Override
  public FinalReportWithInternProfileDto getFinalReportByInternshipAndUser(String internshipTrackingNumber, String userEmail) {
    Internship savedInternship = internshipRepository
            .findByTrackingNumber(internshipTrackingNumber)
            .orElseThrow(() -> new EntityNotFoundException("Internship does not exist"));

    InternshipApplication internshipApplication = savedInternship.getApplications()
            .stream()
            .filter(application -> application.getInternDetails().getUser().getEmail().equals(userEmail))
            .findAny()
            .orElseThrow(() -> new EntityNotFoundException("Internship application for this user does not exist"));

    FinalReport finalReport = internshipApplication.getFinalReports()
            .stream()
            .filter(report -> report.getFinalReportType() == FinalReportType.INTERN)
            .findAny()
            .orElseThrow(() -> new EntityNotFoundException("Intern report not found for this internship"));

    return finalReportMapper.finalReportToFinalReportWithInternProfileDto(finalReport);
  }

  @Override
  public CreateFinalReportResponseDto createFinalReportEmployer(CreateFinalReportRequestDto createFinalReportRequestDto, String userEmail) {
    InternshipApplication application = applicationRepository.findByTrackingNumber(createFinalReportRequestDto.getApplicationTrackingNumber())
            .orElseThrow(() -> new EntityNotFoundException("Internship application does not exist"));

    FinalReport finalReport = finalReportMapper
            .createFinalReport(application, createFinalReportRequestDto, FinalReportType.EMPLOYER);

    finalReportRepository.save(finalReport);

    return new CreateFinalReportResponseDto(true, finalReport.getTrackingNumber());
  }

  @Override
  public CreateFinalReportResponseDto createFinalReportIntern(CreateFinalReportRequestDto createFinalReportRequestDto, String userEmail) {
    Internship internship = internshipRepository.findByTrackingNumber(createFinalReportRequestDto.getInternshipTrackingNumber())
            .orElseThrow(() -> new EntityNotFoundException("Internship does not exist"));

    InternshipApplication internshipApplication = internship.getApplications()
            .stream()
            .filter(application -> application.getInternDetails().getUser().getEmail().equals(userEmail))
            .findAny()
            .orElseThrow(() -> new InsufficientRightsException("User has no rights to make a final report"));

    FinalReport finalReport = finalReportMapper
            .createFinalReport(internshipApplication, createFinalReportRequestDto, FinalReportType.INTERN);

    finalReportRepository.save(finalReport);

    return new CreateFinalReportResponseDto(true, finalReport.getTrackingNumber());
  }

  @Override
  public BaseResponseDto uploadFinalReport(MultipartFile finalReportFile, String reportTrackingNumber) {
    FinalReport finalReport = finalReportRepository.findByTrackingNumber(reportTrackingNumber)
            .orElseThrow(() -> new EntityNotFoundException("Final report does not exist"));

    String originalFileName = finalReportFile.getOriginalFilename();

    if(originalFileName == null) {
      throw new RuntimeException("Could not store the file. Error: invalid file name");
    }

    try {
      String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
      String uniqueFileName = String.format("%s.%s", UUID.randomUUID().toString(), extension);

      Path uploadImagesDirectory = Paths.get(applicationConfig.getFinalReportsDirectory());

      Files.copy(finalReportFile.getInputStream(), uploadImagesDirectory.resolve(uniqueFileName));

      finalReport.setReportFileLocation(applicationConfig.getFinalReportsDirectory() + "/" + uniqueFileName);
      finalReportRepository.save(finalReport);

    } catch (Exception e) {
      throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
    }

    return new BaseResponseDto(true);
  }
}
