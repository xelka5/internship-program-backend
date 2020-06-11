package com.tusofia.internshipprogram.service.impl;

import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.dto.application.ApplicationDetailsDto;
import com.tusofia.internshipprogram.dto.application.InternshipApplicationDto;
import com.tusofia.internshipprogram.entity.application.InternshipApplication;
import com.tusofia.internshipprogram.entity.internship.Internship;
import com.tusofia.internshipprogram.entity.user.User;
import com.tusofia.internshipprogram.exception.AlreadyAppliedException;
import com.tusofia.internshipprogram.exception.EntityNotFoundException;
import com.tusofia.internshipprogram.mapper.ApplicationMapper;
import com.tusofia.internshipprogram.repository.ApplicationRepository;
import com.tusofia.internshipprogram.repository.InternshipRepository;
import com.tusofia.internshipprogram.repository.UserRepository;
import com.tusofia.internshipprogram.service.ApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {

  private ApplicationRepository applicationRepository;
  private InternshipRepository internshipRepository;
  private UserRepository userRepository;
  private ApplicationMapper applicationMapper;

  public ApplicationServiceImpl(ApplicationRepository applicationRepository,
                                InternshipRepository internshipRepository,
                                UserRepository userRepository,
                                ApplicationMapper applicationMapper) {
    this.applicationRepository = applicationRepository;
    this.internshipRepository = internshipRepository;
    this.userRepository = userRepository;
    this.applicationMapper = applicationMapper;
  }

  @Override
  public List<ApplicationDetailsDto> getAllInternApplications(String userEmail) {
    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException("User does not exist"));

    List<InternshipApplication> internshipApplications = savedUser.getInternDetails().getInternshipApplications();

    return applicationMapper.internshipApplicationListToApplicationDetailsDtoList(internshipApplications);
  }

  @Override
  public BaseResponseDto addNewApplication(InternshipApplicationDto internshipApplicationDto, String userEmail) {
    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException("User does not exist"));

    String internshipTrackingNumber = internshipApplicationDto.getInternship().getTrackingNumber();

    Internship internship = internshipRepository.findByTrackingNumber(internshipTrackingNumber)
            .orElseThrow(() -> new EntityNotFoundException("Internship does not exist"));

    boolean alreadyApplied = internship.getApplications()
            .stream()
            .anyMatch(application -> application.getInternDetails().getUser().getEmail().equals(userEmail));

    if(alreadyApplied) {
      throw new AlreadyAppliedException("User has already applied for this internship");
    }

    InternshipApplication newApplication =
            applicationMapper.internshipApplicationDtoToInternshipApplication(internshipApplicationDto);

    newApplication.setInternship(internship);
    newApplication.setInternDetails(savedUser.getInternDetails());

    applicationRepository.save(newApplication);

    return new BaseResponseDto(true);
  }
}