package com.tusofia.internshipprogram.service.impl;

import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.dto.application.ApplicationDetailsDto;
import com.tusofia.internshipprogram.dto.application.ApplicationResponseDto;
import com.tusofia.internshipprogram.dto.application.InternshipApplicationDto;
import com.tusofia.internshipprogram.dto.application.PendingApplicationDto;
import com.tusofia.internshipprogram.entity.application.InternshipApplication;
import com.tusofia.internshipprogram.entity.internship.Internship;
import com.tusofia.internshipprogram.entity.user.EmployerDetails;
import com.tusofia.internshipprogram.entity.user.User;
import com.tusofia.internshipprogram.enumeration.ApplicationStatus;
import com.tusofia.internshipprogram.exception.AlreadyAppliedException;
import com.tusofia.internshipprogram.exception.EntityNotFoundException;
import com.tusofia.internshipprogram.exception.InsufficientRightsException;
import com.tusofia.internshipprogram.exception.InternshipFullException;
import com.tusofia.internshipprogram.mail.applicationResponse.ApplicationResponseMail;
import com.tusofia.internshipprogram.mapper.ApplicationMapper;
import com.tusofia.internshipprogram.repository.ApplicationRepository;
import com.tusofia.internshipprogram.repository.InternshipRepository;
import com.tusofia.internshipprogram.repository.UserRepository;
import com.tusofia.internshipprogram.service.ApplicationService;
import com.tusofia.internshipprogram.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.tusofia.internshipprogram.util.GlobalConstants.*;

@Service
@Slf4j
public class ApplicationServiceImpl implements ApplicationService {

  private static final String ACCEPTED_LABEL = "accepted";
  private static final String REJECTED_LABEL = "rejected";

  private ApplicationRepository applicationRepository;
  private InternshipRepository internshipRepository;
  private UserRepository userRepository;
  private ApplicationMapper applicationMapper;
  private EmailService emailService;

  public ApplicationServiceImpl(ApplicationRepository applicationRepository,
                                InternshipRepository internshipRepository,
                                UserRepository userRepository,
                                ApplicationMapper applicationMapper,
                                EmailService emailService) {
    this.applicationRepository = applicationRepository;
    this.internshipRepository = internshipRepository;
    this.userRepository = userRepository;
    this.applicationMapper = applicationMapper;
    this.emailService = emailService;
  }

  @Override
  public List<ApplicationDetailsDto> getAllInternApplications(String userEmail) {
    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

    List<InternshipApplication> internshipApplications = savedUser.getInternDetails().getInternshipApplications();

    return applicationMapper.internshipApplicationListToApplicationDetailsDtoList(internshipApplications);
  }

  @Override
  public BaseResponseDto addNewApplication(InternshipApplicationDto internshipApplicationDto, String userEmail) {
    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

    String internshipTrackingNumber = internshipApplicationDto.getInternship().getTrackingNumber();

    Internship internship = internshipRepository.findByTrackingNumber(internshipTrackingNumber)
            .orElseThrow(() -> new EntityNotFoundException(INTERNSHIP_NOT_FOUND_MESSAGE));

    boolean alreadyApplied = internship.getApplications()
            .stream()
            .anyMatch(application -> application.getInternDetails().getUser().getEmail().equals(userEmail));

    if (alreadyApplied) {
      throw new AlreadyAppliedException(ALREADY_APPLIED_MESSAGE);
    }

    if (internship.getApplications().size() >= internship.getMaxNumberOfStudents()) {
      throw new InternshipFullException(INTERNSHIP_FULL_MESSAGE);
    }

    InternshipApplication newApplication =
            applicationMapper.internshipApplicationDtoToInternshipApplication(internshipApplicationDto);

    newApplication.setInternship(internship);
    newApplication.setInternDetails(savedUser.getInternDetails());

    applicationRepository.save(newApplication);

    return new BaseResponseDto(true);
  }

  @Override
  public List<PendingApplicationDto> getAllPendingApplications(String userEmail) {
    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

    EmployerDetails employerDetails = savedUser.getEmployerDetails();

    List<InternshipApplication> employersPendingApplications = new ArrayList<>();

    for (Internship internship : employerDetails.getInternships()) {
      employersPendingApplications.addAll(internship.getApplications()
              .stream()
              .filter(app -> app.getStatus() == ApplicationStatus.PENDING)
              .collect(Collectors.toList())
      );
    }

    return applicationMapper.internshipApplicationListToPendingApplicationDtoList(employersPendingApplications);
  }

  @Override
  public BaseResponseDto updateApplicationResponse(ApplicationResponseDto applicationResponseDto, String userEmail) {
    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

    InternshipApplication internshipApplication = savedUser.getEmployerDetails().getInternships()
            .stream()
            .flatMap(internship -> internship.getApplications().stream())
            .filter(internship -> internship.getTrackingNumber().equals(applicationResponseDto.getTrackingNumber()))
            .findAny()
            .orElseThrow(() -> new InsufficientRightsException(CANNOT_CHANGE_INTERNSHIP_STATUS_MESSAGE));

    internshipApplication.setResponseNotes(applicationResponseDto.getResponseNotes());
    internshipApplication.setStatus(applicationResponseDto.getStatus());

    sendApplicationResponseMail(internshipApplication);

    applicationRepository.save(internshipApplication);

    return new BaseResponseDto(true);
  }

  public void sendApplicationResponseMail(InternshipApplication application) {
    String sendToEmail = application.getInternDetails().getUser().getEmail();
    String internshipTitle = application.getInternship().getTitle();
    String response = application.getStatus() == ApplicationStatus.ACCEPTED ? ACCEPTED_LABEL : REJECTED_LABEL;

    ApplicationResponseMail applicationResponseMail = new ApplicationResponseMail(sendToEmail, internshipTitle, response);

    this.emailService.sendMessage(applicationResponseMail);
  }
}
