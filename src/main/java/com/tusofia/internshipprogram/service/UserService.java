package com.tusofia.internshipprogram.service;

import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.dto.emailCheck.EmailCheckRequestDto;
import com.tusofia.internshipprogram.dto.emailCheck.EmailCheckResponseDto;
import com.tusofia.internshipprogram.dto.user.*;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

  UserDetailsResponseDto registerNewUser(UserDetailsDto newUserDetails);

  UserDetailsResponseDto updateUserDetails(UserDetailsDto updatedUserDetails);

  EmailCheckResponseDto checkEmail(EmailCheckRequestDto emailCheckRequest);

  UserDetailsDto getUserDetails(String userEmail);

  UploadImageResponseDto uploadProfileImage(MultipartFile profileImage, String userEmail);

  BaseResponseDto confirmRegistration(RegistrationConfirmRequestDto registrationConfirmRequest);

  BaseResponseDto requestResetPassword(ForgotPasswordRequestDto forgotPasswordRequest);

  BaseResponseDto confirmResetPassword(ResetPasswordRequestDto resetPasswordRequest);
}
