package com.tusofia.internshipprogram.service.impl;

import com.tusofia.internshipprogram.config.ApplicationConfig;
import com.tusofia.internshipprogram.dto.emailCheck.EmailCheckRequestDto;
import com.tusofia.internshipprogram.dto.emailCheck.EmailCheckResponseDto;
import com.tusofia.internshipprogram.dto.user.UploadImageResponseDto;
import com.tusofia.internshipprogram.dto.user.UserDetailsDto;
import com.tusofia.internshipprogram.dto.user.UserDetailsResponseDto;
import com.tusofia.internshipprogram.entity.user.User;
import com.tusofia.internshipprogram.exception.EntityNotFoundException;
import com.tusofia.internshipprogram.mapper.UserMapper;
import com.tusofia.internshipprogram.repository.UserRepository;
import com.tusofia.internshipprogram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private UserMapper userMapper;
  private ApplicationConfig applicationConfig;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, ApplicationConfig applicationConfig) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.applicationConfig = applicationConfig;
  }

  @Override
  public UserDetailsResponseDto registerNewUser(UserDetailsDto newUserDetails) {
    User newUser = userMapper.userDetailsDtoToUserEntity(newUserDetails);

    User savedUser = userRepository.save(newUser);

    return new UserDetailsResponseDto(savedUser.getEmail(), true);
  }

  @Override
  public UserDetailsResponseDto updateUserDetails(UserDetailsDto updatedUserDetails) {
    User savedUser = userRepository.findByEmail(updatedUserDetails.getAccount().getEmail())
            .orElseThrow(() -> new EntityNotFoundException("User does not exist"));

    userMapper.updateUserDetails(updatedUserDetails, savedUser);

    User updatedUser = userRepository.save(savedUser);

    return new UserDetailsResponseDto(updatedUser.getEmail(), true);
  }

  @Override
  public EmailCheckResponseDto checkEmail(EmailCheckRequestDto emailCheckRequest) {
    Optional<User> userEntityOptional = userRepository.findByEmail(emailCheckRequest.getEmailToCheck());

    return new EmailCheckResponseDto(userEntityOptional.isPresent());
  }

  @Override
  public UserDetailsDto getUserDetails(String email) {

    User savedUser = userRepository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("User does not exist"));

    UserDetailsDto userDetailsDto = userMapper.userEntityToUserDetailsDto(savedUser);

    switch(savedUser.getRole()) {
      case INTERN:
        userDetailsDto.setUserDetails(savedUser.getInternDetails());
        break;
      case EMPLOYER:
        userDetailsDto.setUserDetails(savedUser.getEmployerDetails());
        break;
      default:
        break;
    }

    return userDetailsDto;
  }

  @Override
  public UploadImageResponseDto uploadProfileImage(MultipartFile profileImage, String userEmail) {
    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException("User does not exist"));

    String originalImageName = profileImage.getOriginalFilename();

    if(originalImageName == null) {
      throw new RuntimeException("Could not store the file. Error: invalid file name");
    }

    try {
      String extension = originalImageName.substring(originalImageName.lastIndexOf(".") + 1);
      String uniqueImageName = String.format("%s.%s", UUID.randomUUID().toString(), extension);

      Path uploadImagesDirectory = Paths.get(applicationConfig.getProfileImagesDirectory());

      Files.copy(profileImage.getInputStream(), uploadImagesDirectory.resolve(uniqueImageName));

      if(savedUser.getProfileImageName() != null) {
        String oldProfileImage = Arrays.stream(savedUser.getProfileImageName().split("/"))
                .reduce((first, second) -> second)
                .orElse(null);

        Files.delete(uploadImagesDirectory.resolve(oldProfileImage));
      }

      savedUser.setProfileImageName(applicationConfig.getProfileImagesDirectory() + "/" + uniqueImageName);
      userRepository.save(savedUser);

    } catch (Exception e) {
      throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
    }

    return new UploadImageResponseDto(true);
  }
}
