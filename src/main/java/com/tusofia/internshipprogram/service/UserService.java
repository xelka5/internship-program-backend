package com.tusofia.internshipprogram.service;

import com.tusofia.internshipprogram.dto.RegistrationResponseDto;
import com.tusofia.internshipprogram.dto.UserDto;

public interface UserService {

  RegistrationResponseDto registerNewUser(UserDto newUser);
}
