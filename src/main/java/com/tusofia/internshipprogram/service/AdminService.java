package com.tusofia.internshipprogram.service;

import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.dto.admin.UpdatePendingApprovalDto;
import com.tusofia.internshipprogram.dto.finalReport.FinalReportAdminDto;
import com.tusofia.internshipprogram.dto.internship.InternshipExtendedDto;
import com.tusofia.internshipprogram.dto.user.UserDetailsDto;

import java.util.List;

public interface AdminService {

  List<UserDetailsDto> getPendingRegistrationUsers();

  BaseResponseDto updatePendingApproval(UpdatePendingApprovalDto updatePendingApprovalDto);

  List<InternshipExtendedDto> getFinishedInternships();

  FinalReportAdminDto getFinalInternshipReports(String internshipTrackingNumber);
}
