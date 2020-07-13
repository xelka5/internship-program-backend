package com.tusofia.internshipprogram.service;

import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.dto.internship.AssignedInternDto;
import com.tusofia.internshipprogram.dto.internship.FinishInternshipDto;
import com.tusofia.internshipprogram.dto.internship.InternshipDto;
import com.tusofia.internshipprogram.dto.internship.InternshipExtendedDto;
import com.tusofia.internshipprogram.enumeration.InternshipStatus;

import java.util.List;

public interface InternshipService {

  List<InternshipExtendedDto> getAllInternshipsByStatus(InternshipStatus status);

  List<InternshipDto> getEmployerInternshipsByStatus(String userEmail, InternshipStatus status);

  BaseResponseDto addNewInternship(InternshipDto internshipDto, String userEmail);

  BaseResponseDto editInternship(InternshipDto internshipDto, String userEmail);

  InternshipDto getInternshipByTrackingNumber(String trackingNumber, String userEmail);

  List<InternshipExtendedDto> getInternInternshipsByStatus(String userEmail, InternshipStatus status);

  BaseResponseDto deleteInternship(String trackingNumber, String userEmail);

  BaseResponseDto finishInternship(FinishInternshipDto finishInternshipDto, String userEmail);

  List<AssignedInternDto> getAssignedInterns(String trackingNumber, String userEmail);

  List<InternshipExtendedDto> searchActiveInternships(String searchTerm);
}
