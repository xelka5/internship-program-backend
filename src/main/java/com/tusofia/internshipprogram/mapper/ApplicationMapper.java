package com.tusofia.internshipprogram.mapper;

import com.tusofia.internshipprogram.dto.application.ApplicationDetailsDto;
import com.tusofia.internshipprogram.dto.application.PendingApplicationDto;
import com.tusofia.internshipprogram.dto.internship.AssignedInternDto;
import com.tusofia.internshipprogram.dto.user.EmployerProfileDto;
import com.tusofia.internshipprogram.dto.application.ApplicationInternshipDetailsDto;
import com.tusofia.internshipprogram.dto.application.InternshipApplicationDto;
import com.tusofia.internshipprogram.dto.user.InternProfileDto;
import com.tusofia.internshipprogram.entity.application.InternshipApplication;
import com.tusofia.internshipprogram.entity.internship.Internship;
import com.tusofia.internshipprogram.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = UUID.class)
public interface ApplicationMapper {

  List<ApplicationDetailsDto> internshipApplicationListToApplicationDetailsDtoList(List<InternshipApplication> internshipApplicationList);

  @Mapping(target = "internshipDetails", source = "internship")
  ApplicationDetailsDto internshipApplicationToApplicationDetailsDto(InternshipApplication internshipApplication);

  @Mapping(target = "employer", source = "employer.user")
  ApplicationInternshipDetailsDto internshipToApplicationInternshipDetailsDto(Internship internship);

  @Mapping(target = "companyName", source = "employerDetails.companyName")
  EmployerProfileDto userToEmployerProfileDto(User user);

  @Mapping(target = "trackingNumber", expression = "java( UUID.randomUUID().toString() )" )
  InternshipApplication internshipApplicationDtoToInternshipApplication(InternshipApplicationDto internshipApplicationDto);

  List<PendingApplicationDto> internshipApplicationListToPendingApplicationDtoList(List<InternshipApplication> internshipApplicationList);

  @Mapping(target = "intern", source = "internDetails.user")
  @Mapping(target = "internshipDetails", source = "internship")
  PendingApplicationDto internshipApplicationToPendingApplicationDto(InternshipApplication internshipApplication);

  @Mapping(target = "firstName", source = "internDetails.firstName")
  @Mapping(target = "lastName", source = "internDetails.lastName")
  InternProfileDto userToInternProfileDto(User user);

  List<AssignedInternDto> internshipApplicationListToAssignedInternDtoList(List<InternshipApplication> internshipApplicationList);

  @Mapping(target = "firstName", source = "internDetails.firstName")
  @Mapping(target = "lastName", source = "internDetails.lastName")
  @Mapping(target = "university", source = "internDetails.university")
  @Mapping(target = "profileImageName", source = "internDetails.user.profileImageName")
  @Mapping(target = "email", source = "internDetails.user.email")
  AssignedInternDto internshipApplicationToAssignedInternDto(InternshipApplication internshipApplication);
}
