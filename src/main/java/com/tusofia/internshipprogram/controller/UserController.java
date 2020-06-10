package com.tusofia.internshipprogram.controller;

import com.tusofia.internshipprogram.dto.emailCheck.EmailCheckRequestDto;
import com.tusofia.internshipprogram.dto.emailCheck.EmailCheckResponseDto;
import com.tusofia.internshipprogram.dto.user.UserDetailsResponseDto;
import com.tusofia.internshipprogram.dto.user.UploadImageResponseDto;
import com.tusofia.internshipprogram.dto.user.UserDetailsDto;
import com.tusofia.internshipprogram.service.UserService;
import com.tusofia.internshipprogram.util.authentication.AuthenticationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static com.tusofia.internshipprogram.config.OAuth2AuthServerConfig.USER_EMAIL_LABEL;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private static final String PROFILE_IMAGE = "profileImage";
  private static final String EMAIL = "email";
  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public UserDetailsDto getUserDetails(Authentication authentication) {
    return userService.getUserDetails(AuthenticationUtils.extractClaimFromAuthDetails(authentication, USER_EMAIL_LABEL));
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

  @PostMapping("registration/upload")
  public UploadImageResponseDto uploadProfileImage(@RequestParam(PROFILE_IMAGE) MultipartFile profileImage,
                                                   @RequestParam(EMAIL) String userEmail) {
    return userService.uploadProfileImage(profileImage, userEmail);
  }

  @PostMapping("/upload")
  public UploadImageResponseDto reuploadProfileImage(@RequestParam(PROFILE_IMAGE) MultipartFile profileImage,
                                                     @RequestParam(EMAIL) String userEmail) {
     return userService.uploadProfileImage(profileImage, userEmail);
  }
}
