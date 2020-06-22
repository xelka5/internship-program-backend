package com.tusofia.internshipprogram.service;

import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.dto.application.ApplicationDetailsDto;
import com.tusofia.internshipprogram.dto.application.ApplicationResponseDto;
import com.tusofia.internshipprogram.dto.application.InternshipApplicationDto;
import com.tusofia.internshipprogram.dto.application.PendingApplicationDto;

import java.util.List;

public interface ApplicationService {

  List<ApplicationDetailsDto> getAllInternApplications(String userEmail);

  BaseResponseDto addNewApplication(InternshipApplicationDto internshipApplicationDto, String userEmail);

  List<PendingApplicationDto> getAllPendingApplications(String userEmail);

  BaseResponseDto updateApplicationResponse(ApplicationResponseDto applicationResponseDto, String userEmail);
}
