package com.tusofia.internshipprogram.controller;

import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.dto.emailCheck.EmailCheckRequestDto;
import com.tusofia.internshipprogram.dto.emailCheck.EmailCheckResponseDto;
import com.tusofia.internshipprogram.dto.user.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

/**
 * Api description of user operations controller.
 *
 * @author DCvetkov
 * @since 2020
 */
@Api(tags={"User Endpoints"},  description = "User operations")
public interface UserController {

  @ApiOperation(value = "Get User Details",
          notes = "Retrieves information about the currently logged used")
  UserDetailsDto getUserDetails(Authentication authentication);

  @ApiOperation(value = "Get User Details By Email",
          notes = "Retrieves information about the currently logged used by given email")
  UserDetailsDto getUserDetailsByEmail(@ApiParam(value = "User email", required = true) String userEmail);

  @ApiOperation(value = "Request Reset Password",
          notes = "User initiated password reset operation")
  BaseResponseDto requestResetPassword(ForgotPasswordRequestDto forgotPasswordRequest);

  @ApiOperation(value = "Confirm Reset Password",
          notes = "Confirms user authorization for the new password and resets the user password")
  BaseResponseDto confirmResetPassword(ResetPasswordRequestDto resetPasswordRequest);

  @ApiOperation(value = "Register New User",
          notes = "Registers new user with role intern or employer")
  UserDetailsResponseDto registerNewUser(UserDetailsDto registrationRequest);

  @ApiOperation(value = "Update User Details",
          notes = "Updates user information")
  UserDetailsResponseDto updateUserDetails(UserDetailsDto updateRequest);

  @ApiOperation(value = "Check Email",
          notes = "Checks if user with given email is already registered")
  EmailCheckResponseDto checkEmail(EmailCheckRequestDto emailCheckRequest);

  @ApiOperation(value = "Confirm Registration",
          notes = "Admin based confirm registration for employer")
  BaseResponseDto confirmRegistration(RegistrationConfirmRequestDto registrationConfirmRequest);

  @ApiOperation(value = "Upload Profile Image",
          notes = "Uploads profile image for user on the local disk and saves its name in the database")
  UploadImageResponseDto uploadProfileImage(
          @ApiParam(value = "Profile image", required = true) MultipartFile profileImage,
          @ApiParam(value = "User email", required = true) String userEmail);

  @ApiOperation(value = "Reupload Profile Image",
          notes = "Changes profile image for user, deletes the old one and saves the new one")
  UploadImageResponseDto reuploadProfileImage(
          @ApiParam(value = "Profile image", required = true) MultipartFile profileImage,
          @ApiParam(value = "User email", required = true) String userEmail);
}
