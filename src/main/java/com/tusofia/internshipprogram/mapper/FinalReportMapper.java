package com.tusofia.internshipprogram.mapper;

import com.tusofia.internshipprogram.dto.finalReport.*;
import com.tusofia.internshipprogram.entity.application.InternshipApplication;
import com.tusofia.internshipprogram.entity.finalReport.FinalReport;
import com.tusofia.internshipprogram.entity.internship.Internship;
import com.tusofia.internshipprogram.enumeration.FinalReportType;
import org.mapstruct.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = { UUID.class, FinalReportType.class })
public interface FinalReportMapper {

  List<FinalReportWithInternProfileDto> internshipApplicationListToFinalReportDtoList(List<InternshipApplication> internshipApplicationList);

  @Mapping(target = "internProfile", source = "internDetails")
  @Mapping(target = "internProfile.email", source = "internDetails.user.email")
  @Mapping(target = "internProfile.profileImageName", source = "internDetails.user.profileImageName")
  @Mapping(target = "applicationTrackingNumber", source = "trackingNumber")
  FinalReportWithInternProfileDto internshipApplicationToFinalReportEmployerDto(InternshipApplication internshipApplication);

  @AfterMapping
  default void mapEmployerReportInfo(@MappingTarget FinalReportWithInternProfileDto finalReportWithInternProfileDto, InternshipApplication application) {

    Optional<FinalReport> finalReportOptional = application.getFinalReports()
            .stream()
            .filter(report -> report.getFinalReportType() == FinalReportType.EMPLOYER)
            .findAny();

    if(finalReportOptional.isEmpty()) {
      return;
    }

    FinalReport employerReport = finalReportOptional.get();

    finalReportWithInternProfileDto.setReportPresent(true);
    finalReportWithInternProfileDto.setFinalReportType(employerReport.getFinalReportType());
    finalReportWithInternProfileDto.setReportFileLocation(employerReport.getReportFileLocation());
    finalReportWithInternProfileDto.setReportNotes(employerReport.getReportNotes());
  }

  FinalReportWithInternProfileDto finalReportToFinalReportWithInternProfileDto(FinalReport finalReport);

  @Mapping(target = "internshipTitle", source = "internship.title")
  FinalReportEmployerDto internshipAndReportListToFinalReportEmployerDto(Internship internship, List<FinalReportWithInternProfileDto> finalReports);


  List<InternDetailsWithFinalReportsDto> internshipApplicationListToInternDetailsWithFinalReportsDtoList(List<InternshipApplication> internshipApplicationList);

  @Mapping(target = "internProfile", source = "internDetails")
  @Mapping(target = "internProfile.email", source = "internDetails.user.email")
  @Mapping(target = "internProfile.profileImageName", source = "internDetails.user.profileImageName")
  @Mapping(target = "applicationTrackingNumber", source = "internshipApplication.trackingNumber")
  InternDetailsWithFinalReportsDto internshipApplicationToInternDetailsWithFinalReportsDto(InternshipApplication internshipApplication);

  @AfterMapping
  default void mapFinalReports(@MappingTarget InternDetailsWithFinalReportsDto finalReports, InternshipApplication application) {
    FinalReport internFinalReport = application.getFinalReports()
            .stream()
            .filter(report -> report.getFinalReportType() == FinalReportType.INTERN)
            .findAny()
            .orElse(null);

    finalReports.setFinalReportIntern(finalReportToFinalReportDto(internFinalReport));


    FinalReport employerFinalReport = application.getFinalReports()
            .stream()
            .filter(report -> report.getFinalReportType() == FinalReportType.EMPLOYER)
            .findAny()
            .orElse(null);

    finalReports.setFinalReportEmployer(finalReportToFinalReportDto(employerFinalReport));
  }

  List<FinalReportDto> finalReportListToFinalReportDtoList(List<FinalReport> finalReport);

  FinalReportDto finalReportToFinalReportDto(FinalReport finalReport);

  @Mapping(target = "internshipTitle", source = "internship.title")
  @Mapping(target = "companyName", source = "internship.employer.companyName")
  FinalReportAdminDto internshipAndReportListToFinalReportAdminDto(Internship internship, List<InternDetailsWithFinalReportsDto> finalReports);

  @Mapping(target = "trackingNumber", expression = "java( UUID.randomUUID().toString() )" )
  @Mapping(target = "reportNotes", source = "createFinalReportRequestDto.reportNotes")
  @Mapping(target = "internshipApplication", source = "application")
  FinalReport createFinalReport(InternshipApplication application, CreateFinalReportRequestDto createFinalReportRequestDto, FinalReportType finalReportType);
}
