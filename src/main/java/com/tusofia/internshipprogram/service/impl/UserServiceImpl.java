package com.tusofia.internshipprogram.service.impl;

import com.tusofia.internshipprogram.config.ApplicationConfig;
import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.dto.emailCheck.EmailCheckRequestDto;
import com.tusofia.internshipprogram.dto.emailCheck.EmailCheckResponseDto;
import com.tusofia.internshipprogram.dto.user.*;
import com.tusofia.internshipprogram.entity.user.User;
import com.tusofia.internshipprogram.enumeration.UserStatus;
import com.tusofia.internshipprogram.exception.EntityNotFoundException;
import com.tusofia.internshipprogram.exception.FileNotUploadedException;
import com.tusofia.internshipprogram.exception.InsufficientRightsException;
import com.tusofia.internshipprogram.mail.resetPassword.ResetPasswordMail;
import com.tusofia.internshipprogram.mail.userConfirm.UserConfirmationMail;
import com.tusofia.internshipprogram.mapper.UserMapper;
import com.tusofia.internshipprogram.repository.UserRepository;
import com.tusofia.internshipprogram.service.EmailService;
import com.tusofia.internshipprogram.service.UserService;
import com.tusofia.internshipprogram.util.cache.CacheHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static com.tusofia.internshipprogram.util.GlobalConstants.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  private static final String DEFAULT_USER_IMAGE = "images/default_user.png";
  private static final String CANNOT_CONFIRM_REGISTRATION_MESSAGE = "User cannot confirm registration";
  private static final String INVALID_RESET_CODE_MESSAGE = "Invalid reset code, please enter a valid one";

  private UserRepository userRepository;
  private UserMapper userMapper;
  private ApplicationConfig applicationConfig;
  private CacheHelper cacheHelper;
  private EmailService emailService;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, UserMapper userMapper,
                         ApplicationConfig applicationConfig, CacheHelper cacheHelper, EmailService emailService) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.applicationConfig = applicationConfig;
    this.cacheHelper = cacheHelper;
    this.emailService = emailService;
  }

  @Override
  public UserDetailsResponseDto registerNewUser(UserDetailsDto newUserDetails) {
    User newUser = userMapper.userDetailsDtoToUser(newUserDetails);

    User savedUser = userRepository.save(newUser);

    sendConfirmationEmail(savedUser.getEmail());

    return new UserDetailsResponseDto(savedUser.getEmail(), true);
  }

  @Override
  public UserDetailsResponseDto updateUserDetails(UserDetailsDto updatedUserDetails) {
    User savedUser = userRepository.findByEmail(updatedUserDetails.getAccount().getEmail())
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

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
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

    UserDetailsDto userDetailsDto = userMapper.userToUserDetailsDto(savedUser);

    switch (savedUser.getRole()) {
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
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

    String originalImageName = profileImage.getOriginalFilename();

    if (originalImageName == null) {
      throw new FileNotUploadedException(INVALID_FILE_NAME_MESSAGE);
    }

    try {
      String extension = originalImageName.substring(originalImageName.lastIndexOf(".") + 1);
      String uniqueImageName = String.format("%s.%s", UUID.randomUUID().toString(), extension);

      Path uploadImagesDirectory = Paths.get(applicationConfig.getProfileImagesDirectory());

      Files.copy(profileImage.getInputStream(), uploadImagesDirectory.resolve(uniqueImageName));

      if (savedUser.getProfileImageName() != null && !savedUser.getProfileImageName().equals(DEFAULT_USER_IMAGE)) {
        String oldProfileImage = Arrays.stream(savedUser.getProfileImageName().split("/"))
                .reduce((first, second) -> second)
                .orElse(null);

        Files.delete(uploadImagesDirectory.resolve(oldProfileImage));
      }

      savedUser.setProfileImageName(applicationConfig.getProfileImagesDirectory() + "/" + uniqueImageName);
      userRepository.save(savedUser);

    } catch (Exception e) {
      String errorMessage = String.format(STORE_FILE_GENERAL_ERROR_MESSAGE, e.getMessage());

      LOGGER.error(errorMessage);
      throw new FileNotUploadedException(errorMessage);
    }

    return new UploadImageResponseDto(true);
  }

  @Override
  public BaseResponseDto confirmRegistration(RegistrationConfirmRequestDto registrationConfirmRequest) {
    String userEmail = registrationConfirmRequest.getUserEmail();
    String token = cacheHelper.getRegisterConfirmationCache().get(userEmail);

    if (!token.equals(registrationConfirmRequest.getToken())) {
      throw new InsufficientRightsException(CANNOT_CONFIRM_REGISTRATION_MESSAGE);
    }

    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

    savedUser.setUserStatus(UserStatus.ACTIVE);

    userRepository.save(savedUser);

    return new BaseResponseDto(true);
  }

  @Override
  public BaseResponseDto requestResetPassword(ForgotPasswordRequestDto forgotPasswordRequest) {
    String userEmail = forgotPasswordRequest.getUserEmail();

    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

    String resetCode = UUID.randomUUID().toString().substring(0, 7);
    cacheHelper.getForgotPasswordCache().put(userEmail, resetCode);

    ResetPasswordMail resetPasswordMail = new ResetPasswordMail(userEmail, resetCode);

    emailService.sendMessage(resetPasswordMail);

    return new BaseResponseDto(true);
  }

  @Override
  public BaseResponseDto confirmResetPassword(ResetPasswordRequestDto resetPasswordRequest) {
    String userEmail = resetPasswordRequest.getUserEmail();

    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

    String resetCode = cacheHelper.getForgotPasswordCache().get(userEmail);

    if (!resetPasswordRequest.getResetCode().equals(resetCode)) {
      throw new InsufficientRightsException(INVALID_RESET_CODE_MESSAGE);
    }

    savedUser.setPassword(new BCryptPasswordEncoder().encode(resetPasswordRequest.getNewPassword()));

    userRepository.save(savedUser);

    return new BaseResponseDto(true);
  }

  private void sendConfirmationEmail(String userEmail) {
    String generatedToken = UUID.randomUUID().toString();
    cacheHelper.getRegisterConfirmationCache().put(userEmail, generatedToken);

    UserConfirmationMail userConfirmationMail = new UserConfirmationMail(userEmail, generatedToken,
            applicationConfig.getEnvironmentUrl());

    emailService.sendMessage(userConfirmationMail);
  }
}
