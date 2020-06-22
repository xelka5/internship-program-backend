package com.tusofia.internshipprogram.service.impl;

import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.dto.internship.InternshipDto;
import com.tusofia.internshipprogram.dto.internship.InternshipExtendedDto;
import com.tusofia.internshipprogram.entity.application.InternshipApplication;
import com.tusofia.internshipprogram.entity.internship.Internship;
import com.tusofia.internshipprogram.entity.user.User;
import com.tusofia.internshipprogram.enumeration.ApplicationStatus;
import com.tusofia.internshipprogram.exception.EntityNotFoundException;
import com.tusofia.internshipprogram.mapper.InternshipMapper;
import com.tusofia.internshipprogram.repository.InternshipRepository;
import com.tusofia.internshipprogram.repository.UserRepository;
import com.tusofia.internshipprogram.service.InternshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InternshipServiceImpl implements InternshipService {

  private InternshipRepository internshipRepository;
  private InternshipMapper internshipMapper;
  private UserRepository userRepository;

  @Autowired
  public InternshipServiceImpl(InternshipRepository internshipRepository,
                               InternshipMapper internshipMapper,
                               UserRepository userRepository) {
    this.internshipRepository = internshipRepository;
    this.internshipMapper = internshipMapper;
    this.userRepository = userRepository;
  }

  @Override
  public BaseResponseDto addNewInternship(InternshipDto internshipDto, String userEmail) {
    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException("User does not exist"));

    Internship newInternship = internshipMapper.internshipDtoToInternship(internshipDto);
    newInternship.setEmployer(savedUser.getEmployerDetails());

    internshipRepository.save(newInternship);

    return new BaseResponseDto(true);
  }

  @Override
  public List<InternshipDto> getInternships(String userEmail) {
    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException("User does not exist"));

    return internshipMapper.internshipListToInternshipDtoList(savedUser.getEmployerDetails().getInternships());
  }


  @Override
  public List<InternshipExtendedDto> getAllInternships() {
    List<Internship> internshipList = internshipRepository.findAll();

    return internshipMapper.internshipListToInternshipExtendedDtoList(internshipList);
  }

  @Override
  public BaseResponseDto editInternship(InternshipDto internshipDto, String userEmail) {
    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException("User does not exist"));

    List<Internship> internshipList = savedUser.getEmployerDetails().getInternships();

    Internship savedInternship = internshipList.stream()
            .filter(internship -> internship.getTrackingNumber().equals(internshipDto.getTrackingNumber()))
            .findAny().orElseThrow(() -> new EntityNotFoundException("Internship does not exist"));

    internshipMapper.updateInternship(internshipDto, savedInternship);

    internshipRepository.save(savedInternship);

    return new BaseResponseDto(true);
  }

  @Override
  public InternshipDto getInternshipByTrackingNumber(String trackingNumber, String userEmail) {
    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException("User does not exist"));

    List<Internship> internshipList = savedUser.getEmployerDetails().getInternships();

    Internship savedInternship = internshipList.stream()
            .filter(internship -> internship.getTrackingNumber().equals(trackingNumber))
            .findAny().orElseThrow(() -> new EntityNotFoundException("Internship does not exist"));

    return internshipMapper.internshipToInternshipDto(savedInternship);
  }

  @Override
  public List<InternshipExtendedDto> getActiveInternInternships(String userEmail) {
    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException("User does not exist"));

    List<Internship> acceptedInternships = savedUser.getInternDetails().getInternshipApplications()
            .stream()
            .filter(application -> application.getStatus() == ApplicationStatus.ACCEPTED)
            .map(InternshipApplication::getInternship)
            .collect(Collectors.toList());

    return internshipMapper.internshipListToInternshipExtendedDtoList(acceptedInternships);
  }

  @Override
  public BaseResponseDto deleteInternship(String trackingNumber, String userEmail) {
    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException("User does not exist"));

    List<Internship> internshipList = savedUser.getEmployerDetails().getInternships();

    Internship savedInternship = internshipList.stream()
            .filter(internship -> internship.getTrackingNumber().equals(trackingNumber))
            .findAny().orElseThrow(() -> new EntityNotFoundException("Internship does not exist"));

    internshipRepository.delete(savedInternship);

    return new BaseResponseDto(true);
  }

}
