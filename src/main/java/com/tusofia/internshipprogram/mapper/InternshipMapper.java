package com.tusofia.internshipprogram.mapper;

import com.tusofia.internshipprogram.dto.internship.InternshipDto;
import com.tusofia.internshipprogram.entity.internship.Internship;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = UUID.class)
public interface InternshipMapper {

  List<InternshipDto> internshipListToInternshipDtoList(List<Internship> internshipSet);

  @Mapping(target = "trackingNumber", expression = "java( UUID.randomUUID().toString() )" )
  Internship internshipDtoToInternship(InternshipDto internshipDto);

  void updateInternship(InternshipDto internshipDto, @MappingTarget Internship internship);

  InternshipDto internshipToInternshipDto(Internship internship);

}
