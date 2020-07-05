package com.tusofia.internshipprogram.controller;

import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.dto.admin.UpdatePendingApprovalDto;
import com.tusofia.internshipprogram.dto.finalReport.FinalReportAdminDto;
import com.tusofia.internshipprogram.dto.internship.InternshipExtendedDto;
import com.tusofia.internshipprogram.dto.user.UserDetailsDto;
import com.tusofia.internshipprogram.service.AdminService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

  private AdminService adminService;

  public AdminController(AdminService adminService) {
    this.adminService = adminService;
  }

  @GetMapping("/pending")
  public List<UserDetailsDto> getPendingRegistrationUsers() {
    return adminService.getPendingRegistrationUsers();
  }

  @GetMapping("/finished")
  public List<InternshipExtendedDto> getFinishedInternships() {
    return adminService.getFinishedInternships();
  }

  @GetMapping("/report/{internshipTrackingNumber}")
  public FinalReportAdminDto getFinalInternshipReports(@PathVariable String internshipTrackingNumber) {
    return adminService.getFinalInternshipReports(internshipTrackingNumber);
  }

  @PutMapping("/pending")
  public BaseResponseDto updatePendingApproval(@Valid @RequestBody UpdatePendingApprovalDto updatePendingApprovalDto) {
    return adminService.updatePendingApproval(updatePendingApprovalDto);
  }
}
