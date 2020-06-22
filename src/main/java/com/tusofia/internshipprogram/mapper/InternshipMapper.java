package com.tusofia.internshipprogram.mapper;

import com.tusofia.internshipprogram.dto.internship.InternshipDto;
import com.tusofia.internshipprogram.dto.internship.InternshipExtendedDto;
import com.tusofia.internshipprogram.dto.user.EmployerProfileDto;
import com.tusofia.internshipprogram.entity.internship.Internship;
import com.tusofia.internshipprogram.entity.user.User;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = UUID.class)
public interface InternshipMapper {

  List<InternshipExtendedDto> internshipListToInternshipExtendedDtoList(List<Internship> internshipList);

  @Mapping(target = "employer", source = "employer.user")
  InternshipExtendedDto internshipToInternshipExtendedDto(Internship internship);

  @Mapping(target = "companyName", source = "employerDetails.companyName")
  EmployerProfileDto userToApplicationEmployerDetailsDto(User user);

  List<InternshipDto> internshipListToInternshipDtoList(List<Internship> internshipList);

  @Mapping(target = "trackingNumber", expression = "java( UUID.randomUUID().toString() )" )
  Internship internshipDtoToInternship(InternshipDto internshipDto);

  void updateInternship(InternshipDto internshipDto, @MappingTarget Internship internship);

  InternshipDto internshipToInternshipDto(Internship internship);

}
