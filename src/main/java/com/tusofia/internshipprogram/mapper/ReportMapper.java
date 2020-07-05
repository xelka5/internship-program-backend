package com.tusofia.internshipprogram.mapper;

import com.tusofia.internshipprogram.dto.report.InternReportDto;
import com.tusofia.internshipprogram.entity.internship.Internship;
import com.tusofia.internshipprogram.entity.report.InternReport;
import com.tusofia.internshipprogram.entity.user.User;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = UUID.class)
public interface ReportMapper {

  List<InternReportDto> internReportListToInternReportDtoList(List<InternReport> internReportList);

  InternReportDto internReportToInternReportDto(InternReport internReport);

  void updateReport(InternReportDto internReportDto, @MappingTarget InternReport internReport);

  @Mapping(target = "internDetails", source = "user.internDetails")
  @Mapping(target = "trackingNumber", expression = "java( UUID.randomUUID().toString() )" )
  InternReport internReportDtoUserAndInternshipToInternReport(InternReportDto internReportDto,
                                                              User user, Internship internship);

}
