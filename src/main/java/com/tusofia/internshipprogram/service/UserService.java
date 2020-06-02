package com.tusofia.internshipprogram.service;

import com.tusofia.internshipprogram.dto.emailCheck.EmailCheckRequestDto;
import com.tusofia.internshipprogram.dto.emailCheck.EmailCheckResponseDto;
import com.tusofia.internshipprogram.dto.registration.RegistrationResponseDto;
import com.tusofia.internshipprogram.dto.registration.UserDetailsDto;

public interface UserService {

  RegistrationResponseDto registerNewUser(UserDetailsDto newUserDetails);

  EmailCheckResponseDto checkEmail(EmailCheckRequestDto emailCheckRequest);

  UserDetailsDto getUserDetails(Long userId);
}
