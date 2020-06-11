package com.tusofia.internshipprogram.service;

import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.dto.application.ApplicationDetailsDto;
import com.tusofia.internshipprogram.dto.application.InternshipApplicationDto;

import java.util.List;

public interface ApplicationService {

  List<ApplicationDetailsDto> getAllInternApplications(String userEmail);

  BaseResponseDto addNewApplication(InternshipApplicationDto internshipApplicationDto, String userEmail);
}
