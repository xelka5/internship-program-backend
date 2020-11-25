package com.tusofia.internshipprogram.controller.impl;

import com.tusofia.internshipprogram.controller.UserController;
import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.dto.emailCheck.EmailCheckRequestDto;
import com.tusofia.internshipprogram.dto.emailCheck.EmailCheckResponseDto;
import com.tusofia.internshipprogram.dto.user.*;
import com.tusofia.internshipprogram.service.UserService;
import com.tusofia.internshipprogram.util.authentication.AuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static com.tusofia.internshipprogram.config.OAuth2AuthServerConfig.USER_EMAIL_LABEL;

/**
 * Url mapping and implementation of {@link UserController}
 *
 * @author DCvetkov
 * @since 2020
 */
@RestController
@RequestMapping("/api/users")
public class UserControllerImpl implements UserController {

  private static final String PROFILE_IMAGE_PARAM = "profileImage";
  private static final String EMAIL_PARAM = "email";
  private static final String USER_EMAIL_PARAM = "userEmail";

  private UserService userService;

  @Autowired
  public UserControllerImpl(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public UserDetailsDto getUserDetails(Authentication authentication) {
    return userService.getUserDetails(AuthenticationUtils.extractClaimFromAuthDetails(authentication, USER_EMAIL_LABEL));
  }

  @GetMapping("/email")
  public UserDetailsDto getUserDetailsByEmail(@RequestParam(USER_EMAIL_PARAM) String userEmail) {
    return userService.getUserDetails(userEmail);
  }

  @PostMapping("/reset-password/request")
  public BaseResponseDto requestResetPassword(@Valid @RequestBody ForgotPasswordRequestDto forgotPasswordRequest) {
    return userService.requestResetPassword(forgotPasswordRequest);
  }

  @PostMapping("/reset-password/confirm")
  public BaseResponseDto confirmResetPassword(@Valid @RequestBody ResetPasswordRequestDto resetPasswordRequest) {
    return userService.confirmResetPassword(resetPasswordRequest);
  }

  @PostMapping("/registration")
  public UserDetailsResponseDto registerNewUser(@Valid @RequestBody UserDetailsDto registrationRequest) {
    return userService.registerNewUser(registrationRequest);
  }

  @PutMapping
  public UserDetailsResponseDto updateUserDetails(@Valid @RequestBody UserDetailsDto updateRequest) {
    return userService.updateUserDetails(updateRequest);
  }

  @PostMapping("/registration/email-check")
  public EmailCheckResponseDto checkEmail(@Valid @RequestBody EmailCheckRequestDto emailCheckRequest) {
    return userService.checkEmail(emailCheckRequest);
  }

  @PostMapping("/registration/confirm")
  public BaseResponseDto confirmRegistration(@Valid @RequestBody RegistrationConfirmRequestDto registrationConfirmRequest) {
    return userService.confirmRegistration(registrationConfirmRequest);
  }

  @PostMapping("registration/upload")
  public UploadImageResponseDto uploadProfileImage(@RequestParam(PROFILE_IMAGE_PARAM) MultipartFile profileImage,
                                                   @RequestParam(EMAIL_PARAM) String userEmail) {

    return userService.uploadProfileImage(profileImage, userEmail);
  }

  @PostMapping("/upload")
  public UploadImageResponseDto reuploadProfileImage(@RequestParam(PROFILE_IMAGE_PARAM) MultipartFile profileImage,
                                                     @RequestParam(EMAIL_PARAM) String userEmail) {

     return userService.uploadProfileImage(profileImage, userEmail);
  }
}
