package com.tusofia.internshipprogram.service.impl;

import com.tusofia.internshipprogram.config.ApplicationConfig;
import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.dto.finalReport.CreateFinalReportRequestDto;
import com.tusofia.internshipprogram.dto.finalReport.CreateFinalReportResponseDto;
import com.tusofia.internshipprogram.dto.finalReport.FinalReportEmployerDto;
import com.tusofia.internshipprogram.dto.finalReport.FinalReportWithInternProfileDto;
import com.tusofia.internshipprogram.entity.application.InternshipApplication;
import com.tusofia.internshipprogram.entity.finalReport.FinalReport;
import com.tusofia.internshipprogram.entity.internship.Internship;
import com.tusofia.internshipprogram.entity.user.InternDetails;
import com.tusofia.internshipprogram.enumeration.ApplicationStatus;
import com.tusofia.internshipprogram.enumeration.FinalReportType;
import com.tusofia.internshipprogram.exception.EntityNotFoundException;
import com.tusofia.internshipprogram.exception.FileNotUploadedException;
import com.tusofia.internshipprogram.exception.InsufficientRightsException;
import com.tusofia.internshipprogram.mapper.FinalReportMapper;
import com.tusofia.internshipprogram.repository.ApplicationRepository;
import com.tusofia.internshipprogram.repository.FinalReportRepository;
import com.tusofia.internshipprogram.repository.InternshipRepository;
import com.tusofia.internshipprogram.service.FinalReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.tusofia.internshipprogram.util.GlobalConstants.*;

@Service
@Slf4j
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
            .orElseThrow(() -> new EntityNotFoundException(INTERNSHIP_NOT_FOUND_MESSAGE));

    if (!savedInternship.getEmployer().getUser().getEmail().equals(userEmail)) {
      throw new InsufficientRightsException(INSUFFICIENT_RIGHTS_FINAL_REPORT_MESSAGE);
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
            .orElseThrow(() -> new EntityNotFoundException(INTERNSHIP_NOT_FOUND_MESSAGE));

    InternshipApplication internshipApplication = savedInternship.getApplications()
            .stream()
            .filter(application -> application.getInternDetails().getUser().getEmail().equals(userEmail))
            .findAny()
            .orElseThrow(() -> new EntityNotFoundException(INTERNSHIP_APPLICATION_NOT_FOUND_MESSAGE));

    FinalReport finalReport = internshipApplication.getFinalReports()
            .stream()
            .filter(report -> report.getFinalReportType() == FinalReportType.INTERN)
            .findAny()
            .orElseThrow(() -> new EntityNotFoundException(REPORT_NOT_FOUND_FOR_INTERNSHIP_MESSAGE));

    return finalReportMapper.finalReportToFinalReportWithInternProfileDto(finalReport);
  }

  @Override
  public CreateFinalReportResponseDto createFinalReportEmployer(CreateFinalReportRequestDto createFinalReportRequestDto, String userEmail) {
    InternshipApplication application = applicationRepository.findByTrackingNumber(createFinalReportRequestDto.getApplicationTrackingNumber())
            .orElseThrow(() -> new EntityNotFoundException(INTERNSHIP_APPLICATION_NOT_FOUND_MESSAGE));

    FinalReport finalReport = finalReportMapper
            .createFinalReport(application, createFinalReportRequestDto, FinalReportType.EMPLOYER);

    finalReportRepository.save(finalReport);

    return new CreateFinalReportResponseDto(true, finalReport.getTrackingNumber());
  }

  @Override
  public CreateFinalReportResponseDto createFinalReportIntern(CreateFinalReportRequestDto createFinalReportRequestDto, String userEmail) {
    Internship internship = internshipRepository.findByTrackingNumber(createFinalReportRequestDto.getInternshipTrackingNumber())
            .orElseThrow(() -> new EntityNotFoundException(INTERNSHIP_NOT_FOUND_MESSAGE));

    InternshipApplication internshipApplication = internship.getApplications()
            .stream()
            .filter(application -> application.getInternDetails().getUser().getEmail().equals(userEmail))
            .findAny()
            .orElseThrow(() -> new InsufficientRightsException(INSUFFICIENT_RIGHTS_FINAL_REPORT_MESSAGE));

    FinalReport finalReport = finalReportMapper
            .createFinalReport(internshipApplication, createFinalReportRequestDto, FinalReportType.INTERN);

    finalReportRepository.save(finalReport);

    return new CreateFinalReportResponseDto(true, finalReport.getTrackingNumber());
  }

  @Override
  public BaseResponseDto uploadFinalReport(MultipartFile finalReportFile, String reportTrackingNumber) {
    FinalReport finalReport = finalReportRepository.findByTrackingNumber(reportTrackingNumber)
            .orElseThrow(() -> new EntityNotFoundException(FINAL_REPORT_NOT_FOUND_MESSAGE));

    String originalFileName = finalReportFile.getOriginalFilename();

    if (originalFileName == null) {
      throw new FileNotUploadedException(INVALID_FILE_NAME_MESSAGE);
    }

    try {
      String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);

      String uniqueFileName = createFinalReportFileName(finalReport, extension);

      Path uploadImagesDirectory = Paths.get(applicationConfig.getFinalReportsDirectory());

      Files.copy(finalReportFile.getInputStream(), uploadImagesDirectory.resolve(uniqueFileName));

      finalReport.setReportFileLocation(applicationConfig.getFinalReportsDirectory() + "/" + uniqueFileName);
      finalReportRepository.save(finalReport);

    } catch (Exception e) {
      String errorMessage = String.format(STORE_FILE_GENERAL_ERROR_MESSAGE, e.getMessage());

      LOGGER.error(errorMessage);
      throw new FileNotUploadedException(errorMessage);
    }

    return new BaseResponseDto(true);
  }


  private String createFinalReportFileName(FinalReport finalReport, String extension) {

    if(FinalReportType.EMPLOYER == finalReport.getFinalReportType()) {
      Internship internship = finalReport.getInternshipApplication().getInternship();

      return String.join("_", internship.getEmployer().getCompanyName(), "final", "report", String.valueOf(Instant.now().toEpochMilli()))
              .concat(".")
              .concat(extension)
              .replace(" ", "_")
              .toLowerCase();

    } else if(FinalReportType.INTERN == finalReport.getFinalReportType()) {
      InternDetails internDetails = finalReport.getInternshipApplication().getInternDetails();

      return String.join("_", internDetails.getFirstName(), internDetails.getLastName(), "final", "report", String.valueOf(Instant.now().toEpochMilli()))
              .concat(".")
              .concat(extension)
              .replace(" ", "_")
              .toLowerCase();

    } else {
      throw new RuntimeException();
    }
  }
}
