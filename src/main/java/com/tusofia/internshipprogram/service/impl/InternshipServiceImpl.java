package com.tusofia.internshipprogram.service.impl;

import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.dto.internship.AssignedInternDto;
import com.tusofia.internshipprogram.dto.internship.FinishInternshipDto;
import com.tusofia.internshipprogram.dto.internship.InternshipDto;
import com.tusofia.internshipprogram.dto.internship.InternshipExtendedDto;
import com.tusofia.internshipprogram.entity.application.InternshipApplication;
import com.tusofia.internshipprogram.entity.internship.Internship;
import com.tusofia.internshipprogram.entity.user.User;
import com.tusofia.internshipprogram.enumeration.ApplicationStatus;
import com.tusofia.internshipprogram.enumeration.InternshipStatus;
import com.tusofia.internshipprogram.exception.EntityNotFoundException;
import com.tusofia.internshipprogram.exception.InsufficientRightsException;
import com.tusofia.internshipprogram.mail.internshipDeleted.InternshipDeletedMail;
import com.tusofia.internshipprogram.mapper.ApplicationMapper;
import com.tusofia.internshipprogram.mapper.InternshipMapper;
import com.tusofia.internshipprogram.repository.InternshipRepository;
import com.tusofia.internshipprogram.repository.UserRepository;
import com.tusofia.internshipprogram.service.EmailService;
import com.tusofia.internshipprogram.service.InternshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.tusofia.internshipprogram.util.GlobalConstants.*;


@Service
public class InternshipServiceImpl implements InternshipService {

  private InternshipRepository internshipRepository;
  private InternshipMapper internshipMapper;
  private ApplicationMapper applicationMapper;
  private UserRepository userRepository;
  private EmailService emailService;

  @Autowired
  public InternshipServiceImpl(InternshipRepository internshipRepository,
                               InternshipMapper internshipMapper,
                               ApplicationMapper applicationMapper,
                               UserRepository userRepository,
                               EmailService emailService) {
    this.internshipRepository = internshipRepository;
    this.internshipMapper = internshipMapper;
    this.applicationMapper = applicationMapper;
    this.userRepository = userRepository;
    this.emailService = emailService;
  }

  @Override
  public BaseResponseDto addNewInternship(InternshipDto internshipDto, String userEmail) {
    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

    Internship newInternship = internshipMapper.internshipDtoToInternship(internshipDto);
    newInternship.setEmployer(savedUser.getEmployerDetails());

    internshipRepository.save(newInternship);

    return new BaseResponseDto(true);
  }

  @Override
  public List<InternshipDto> getEmployerInternshipsByStatus(String userEmail, InternshipStatus status) {
    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

    List<Internship> activeEmployerInternships = savedUser.getEmployerDetails().getInternships()
            .stream()
            .filter(internship -> internship.getStatus() == status)
            .collect(Collectors.toList());

    return internshipMapper.internshipListToInternshipDtoList(activeEmployerInternships);
  }


  @Override
  public List<InternshipExtendedDto> getAllInternshipsByStatus(InternshipStatus status) {
    List<Internship> internshipList = internshipRepository.findByStatusOrderByCreateDateDesc(status);

    return internshipMapper.internshipListToInternshipExtendedDtoList(internshipList);
  }

  @Override
  public BaseResponseDto editInternship(InternshipDto internshipDto, String userEmail) {
    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

    List<Internship> internshipList = savedUser.getEmployerDetails().getInternships();

    Internship savedInternship = internshipList
            .stream()
            .filter(internship -> internship.getTrackingNumber().equals(internshipDto.getTrackingNumber()))
            .findAny()
            .orElseThrow(() -> new EntityNotFoundException(INTERNSHIP_NOT_FOUND_MESSAGE));

    internshipMapper.updateInternship(internshipDto, savedInternship);

    internshipRepository.save(savedInternship);

    return new BaseResponseDto(true);
  }

  @Override
  public InternshipDto getInternshipByTrackingNumber(String trackingNumber, String userEmail) {
    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

    List<Internship> internshipList = savedUser.getEmployerDetails().getInternships();

    Internship savedInternship = internshipList
            .stream()
            .filter(internship -> internship.getTrackingNumber().equals(trackingNumber))
            .findAny()
            .orElseThrow(() -> new EntityNotFoundException(INTERNSHIP_NOT_FOUND_MESSAGE));

    return internshipMapper.internshipToInternshipDto(savedInternship);
  }

  @Override
  public List<InternshipExtendedDto> getInternInternshipsByStatus(String userEmail, InternshipStatus internshipStatus) {
    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

    List<InternshipApplication> acceptedApplications = savedUser.getInternDetails().getInternshipApplications()
            .stream()
            .filter(application -> application.getStatus() == ApplicationStatus.ACCEPTED &&
                    application.getInternship().getStatus() == internshipStatus)
            .collect(Collectors.toList());

    if (InternshipStatus.FINISHED == internshipStatus) {
      return internshipMapper.internshipApplicationListToInternshipExtendedDtoList(acceptedApplications);
    } else {
      List<Internship> acceptedInternships = acceptedApplications
              .stream()
              .map(InternshipApplication::getInternship)
              .collect(Collectors.toList());

      return internshipMapper.internshipListToInternshipExtendedDtoList(acceptedInternships);
    }
  }

  @Override
  public BaseResponseDto deleteInternship(String trackingNumber, String userEmail) {
    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

    List<Internship> internshipList = savedUser.getEmployerDetails().getInternships();

    Internship savedInternship = internshipList
            .stream()
            .filter(internship -> internship.getTrackingNumber().equals(trackingNumber))
            .findAny()
            .orElseThrow(() -> new EntityNotFoundException(INTERNSHIP_NOT_FOUND_MESSAGE));

    savedInternship.setStatus(InternshipStatus.CANCELLED);

    savedInternship.getApplications().forEach(application -> {
      application.setStatus(ApplicationStatus.CANCELLED);
      sendInternshipDeletedMail(application, savedInternship);
    });

    internshipRepository.save(savedInternship);

    return new BaseResponseDto(true);
  }

  private void sendInternshipDeletedMail(InternshipApplication application, Internship savedInternship) {
    String sendToEmail = application.getInternDetails().getUser().getEmail();
    String companyName = savedInternship.getEmployer().getCompanyName();
    InternshipDeletedMail internshipDeletedMail = new InternshipDeletedMail(sendToEmail, companyName, savedInternship.getTitle());

    emailService.sendMessage(internshipDeletedMail);
  }

  @Override
  public BaseResponseDto finishInternship(FinishInternshipDto finishInternshipDto, String userEmail) {
    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

    List<Internship> internshipList = savedUser.getEmployerDetails().getInternships();

    Internship savedInternship = internshipList
            .stream()
            .filter(internship -> internship.getTrackingNumber().equals(finishInternshipDto.getTrackingNumber()))
            .findAny()
            .orElseThrow(() -> new EntityNotFoundException(INTERNSHIP_NOT_FOUND_MESSAGE));

    if (savedInternship.getStatus() != InternshipStatus.ACTIVE) {
      throw new InsufficientRightsException(ACTIVE_INTERNSHIPS_CAN_BE_FINISHED_MESSAGE);
    }

    savedInternship.setStatus(InternshipStatus.FINISHED);

    internshipRepository.save(savedInternship);

    return new BaseResponseDto(true);
  }

  @Override
  public List<AssignedInternDto> getAssignedInterns(String trackingNumber, String userEmail) {
    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

    List<Internship> internshipList = savedUser.getEmployerDetails().getInternships();

    Internship savedInternship = internshipList
            .stream()
            .filter(internship -> internship.getTrackingNumber().equals(trackingNumber))
            .findAny()
            .orElseThrow(() -> new EntityNotFoundException(INTERNSHIP_NOT_FOUND_MESSAGE));

    List<InternshipApplication> assignedInterns = savedInternship.getApplications()
            .stream()
            .filter(application -> application.getStatus() == ApplicationStatus.ACCEPTED)
            .collect(Collectors.toList());

    return applicationMapper.internshipApplicationListToAssignedInternDtoList(assignedInterns);
  }

  @Override
  public List<InternshipExtendedDto> searchActiveInternships(String searchTerm) {
    List<Internship> filteredInternships =
            internshipRepository.findByStatusAndTitleContainingOrDescriptionContaining(InternshipStatus.ACTIVE, searchTerm, searchTerm);

    return internshipMapper.internshipListToInternshipExtendedDtoList(filteredInternships);
  }

}
