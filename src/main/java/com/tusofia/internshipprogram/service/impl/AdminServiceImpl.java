package com.tusofia.internshipprogram.service.impl;

import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.dto.admin.UpdatePendingApprovalDto;
import com.tusofia.internshipprogram.dto.finalReport.FinalReportAdminDto;
import com.tusofia.internshipprogram.dto.finalReport.InternDetailsWithFinalReportsDto;
import com.tusofia.internshipprogram.dto.internship.InternshipExtendedDto;
import com.tusofia.internshipprogram.dto.user.UserDetailsDto;
import com.tusofia.internshipprogram.entity.application.InternshipApplication;
import com.tusofia.internshipprogram.entity.internship.Internship;
import com.tusofia.internshipprogram.entity.user.User;
import com.tusofia.internshipprogram.enumeration.ApplicationStatus;
import com.tusofia.internshipprogram.enumeration.InternshipStatus;
import com.tusofia.internshipprogram.enumeration.UserStatus;
import com.tusofia.internshipprogram.exception.EntityNotFoundException;
import com.tusofia.internshipprogram.mapper.AdminMapper;
import com.tusofia.internshipprogram.mapper.FinalReportMapper;
import com.tusofia.internshipprogram.mapper.InternshipMapper;
import com.tusofia.internshipprogram.repository.InternshipRepository;
import com.tusofia.internshipprogram.repository.UserRepository;
import com.tusofia.internshipprogram.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.tusofia.internshipprogram.util.GlobalConstants.INTERNSHIP_NOT_FOUND_MESSAGE;
import static com.tusofia.internshipprogram.util.GlobalConstants.USER_NOT_FOUND_MESSAGE;

@Service
public class AdminServiceImpl implements AdminService {

  private UserRepository userRepository;
  private InternshipRepository internshipRepository;
  private AdminMapper adminMapper;
  private InternshipMapper internshipMapper;
  private FinalReportMapper finalReportMapper;

  public AdminServiceImpl(UserRepository userRepository, InternshipRepository internshipRepository,
                          AdminMapper adminMapper, InternshipMapper internshipMapper,
                          FinalReportMapper finalReportMapper) {
    this.userRepository = userRepository;
    this.internshipRepository = internshipRepository;
    this.adminMapper = adminMapper;
    this.internshipMapper = internshipMapper;
    this.finalReportMapper = finalReportMapper;
  }

  @Override
  public List<UserDetailsDto> getPendingRegistrationUsers() {

    List<User> pendingUsers = userRepository.findByUserAllowed(Boolean.FALSE);

    return adminMapper.pendingUserListToUserDetailsDtoList(pendingUsers);
  }

  @Override
  public List<InternshipExtendedDto> getFinishedInternships() {
    List<Internship> internships = internshipRepository.findByStatusOrderByCreateDateDesc(InternshipStatus.FINISHED);

    return internshipMapper.internshipListToInternshipExtendedDtoList(internships);
  }


  @Override
  public BaseResponseDto updatePendingApproval(UpdatePendingApprovalDto updatePendingApprovalDto) {
    User updatingUser = userRepository.findByEmail(updatePendingApprovalDto.getUserEmail())
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

    if (updatePendingApprovalDto.getUserAllowed()) {
      updatingUser.setUserAllowed(Boolean.TRUE);
    } else {
      updatingUser.setUserStatus(UserStatus.BLOCKED);
    }

    userRepository.save(updatingUser);

    return new BaseResponseDto(true);
  }

  @Override
  public FinalReportAdminDto getFinalInternshipReports(String internshipTrackingNumber) {
    Internship internship = internshipRepository.findByTrackingNumber(internshipTrackingNumber)
            .orElseThrow(() -> new EntityNotFoundException(INTERNSHIP_NOT_FOUND_MESSAGE));

    List<InternshipApplication> acceptedInterns = internship.getApplications()
            .stream()
            .filter(application -> application.getStatus() == ApplicationStatus.ACCEPTED)
            .collect(Collectors.toList());

    List<InternDetailsWithFinalReportsDto> finalReports = finalReportMapper
            .internshipApplicationListToInternDetailsWithFinalReportsDtoList(acceptedInterns);

    return finalReportMapper.internshipAndReportListToFinalReportAdminDto(internship, finalReports);
  }
}
