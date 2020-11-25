package com.tusofia.internshipprogram.controller.impl;

import com.tusofia.internshipprogram.controller.AdminController;
import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.dto.admin.UpdatePendingApprovalDto;
import com.tusofia.internshipprogram.dto.finalReport.FinalReportAdminDto;
import com.tusofia.internshipprogram.dto.internship.InternshipExtendedDto;
import com.tusofia.internshipprogram.dto.user.UserDetailsDto;
import com.tusofia.internshipprogram.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Url mapping and implementation of {@link AdminController}
 *
 * @author DCvetkov
 * @since 2020
 */
@RestController
@RequestMapping("/api/admin")
public class AdminControllerImpl implements AdminController {

  private AdminService adminService;

  @Autowired
  public AdminControllerImpl(AdminService adminService) {
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
