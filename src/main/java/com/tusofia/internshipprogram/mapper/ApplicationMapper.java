package com.tusofia.internshipprogram.mapper;

import com.tusofia.internshipprogram.dto.application.ApplicationDetailsDto;
import com.tusofia.internshipprogram.dto.application.ApplicationEmployerDetailsDto;
import com.tusofia.internshipprogram.dto.application.ApplicationInternshipDetailsDto;
import com.tusofia.internshipprogram.dto.application.InternshipApplicationDto;
import com.tusofia.internshipprogram.entity.application.InternshipApplication;
import com.tusofia.internshipprogram.entity.internship.Internship;
import com.tusofia.internshipprogram.entity.user.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ApplicationMapper {

  List<ApplicationDetailsDto> internshipApplicationListToApplicationDetailsDtoList(List<InternshipApplication> internshipApplicationList);

  @Mapping(target = "internshipDetails", source = "internship")
  ApplicationDetailsDto internshipApplicationToApplicationDetailsDto(InternshipApplication internshipApplication);

  @Mapping(target = "employer", source = "employer.user")
  ApplicationInternshipDetailsDto internshipToApplicationInternshipDetailsDto(Internship internship);

  @Mapping(target = "companyName", source = "employerDetails.companyName")
  ApplicationEmployerDetailsDto userToApplicationEmployerDetailsDto(User user);


  InternshipApplication internshipApplicationDtoToInternshipApplication(InternshipApplicationDto internshipApplicationDto);



}
