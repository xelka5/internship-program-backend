package com.tusofia.internshipprogram.service;

import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.dto.internship.InternshipDto;
import com.tusofia.internshipprogram.dto.internship.InternshipExtendedDto;

import java.util.List;

public interface InternshipService {

  List<InternshipExtendedDto> getAllInternships();

  List<InternshipDto> getInternships(String userEmail);

  BaseResponseDto addNewInternship(InternshipDto internshipDto, String userEmail);

  BaseResponseDto editInternship(InternshipDto internshipDto, String userEmail);

  InternshipDto getInternshipByTrackingNumber(String trackingNumber, String userEmail);

  List<InternshipExtendedDto> getActiveInternInternships(String userEmail);

  BaseResponseDto deleteInternship(String trackingNumber, String userEmail);
}
