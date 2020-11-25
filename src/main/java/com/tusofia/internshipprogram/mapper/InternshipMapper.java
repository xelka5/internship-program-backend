package com.tusofia.internshipprogram.mapper;

import com.tusofia.internshipprogram.dto.internship.InternshipDto;
import com.tusofia.internshipprogram.dto.internship.InternshipExtendedDto;
import com.tusofia.internshipprogram.dto.user.EmployerProfileDto;
import com.tusofia.internshipprogram.entity.application.InternshipApplication;
import com.tusofia.internshipprogram.entity.internship.Internship;
import com.tusofia.internshipprogram.entity.user.User;
import com.tusofia.internshipprogram.enumeration.ApplicationStatus;
import com.tusofia.internshipprogram.enumeration.FinalReportType;
import com.tusofia.internshipprogram.enumeration.InternshipStatus;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = { UUID.class, InternshipStatus.class})
public interface InternshipMapper {

  List<InternshipExtendedDto> internshipListToInternshipExtendedDtoList(List<Internship> internshipList);

  @Mapping(target = "employer", source = "employer.user")
  InternshipExtendedDto internshipToInternshipExtendedDto(Internship internship);

  @Mapping(target = "companyName", source = "employerDetails.companyName")
  EmployerProfileDto userToApplicationEmployerDetailsDto(User user);

  List<InternshipDto> internshipListToInternshipDtoList(List<Internship> internshipList);

  @Mapping(target = "trackingNumber", expression = "java( UUID.randomUUID().toString() )" )
  @Mapping(target = "status", expression = "java( InternshipStatus.ACTIVE )" )
  Internship internshipDtoToInternship(InternshipDto internshipDto);

  void updateInternship(InternshipDto internshipDto, @MappingTarget Internship internship);

  InternshipDto internshipToInternshipDto(Internship internship);

  @AfterMapping
  default void mapNumberOfAssignedInterns(@MappingTarget InternshipDto internshipDto, Internship internship) {

    long numberOfAssignedStudents = internship.getApplications()
            .stream()
            .filter(application -> application.getStatus() == ApplicationStatus.ACCEPTED)
            .count();

    internshipDto.setNumberOfAssignedStudents(numberOfAssignedStudents);
  }

  List<InternshipExtendedDto> internshipApplicationListToInternshipExtendedDtoList(List<InternshipApplication> internshipApplications);

  @Mapping(target = "trackingNumber", source = "internship.trackingNumber")
  @Mapping(target = "title", source = "internship.title")
  @Mapping(target = "description", source = "internship.description")
  @Mapping(target = "startDate", source = "internship.startDate")
  @Mapping(target = "duration", source = "internship.duration")
  @Mapping(target = "durationUnit", source = "internship.durationUnit")
  @Mapping(target = "type", source = "internship.type")
  @Mapping(target = "salary", source = "internship.salary")
  @Mapping(target = "currency", source = "internship.currency")
  @Mapping(target = "employer", source = "internship.employer.user")
  InternshipExtendedDto internshipApplicationToInternshipExtendedDto(InternshipApplication application);

  @AfterMapping
  default void mapInternFinalReportPresent(@MappingTarget InternshipExtendedDto internshipExtendedDto, InternshipApplication application) {
    boolean reportIsPresent = application.getFinalReports()
            .stream()
            .anyMatch(report -> report.getFinalReportType() == FinalReportType.INTERN);

    internshipExtendedDto.setInternReportPresent(reportIsPresent);
  }


}
