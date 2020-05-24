package com.tusofia.internshipprogram.service.impl;

import com.tusofia.internshipprogram.dto.RegistrationResponseDto;
import com.tusofia.internshipprogram.dto.UserDto;
import com.tusofia.internshipprogram.entity.UserEntity;
import com.tusofia.internshipprogram.repository.UserRepository;
import com.tusofia.internshipprogram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public RegistrationResponseDto registerNewUser(UserDto newUser) {
    UserEntity savedUser = userRepository.save(new UserEntity(newUser.getUsername(),
            passwordEncoder.encode(newUser.getPassword()),
            newUser.getEmail(), newUser.getRole()));

    return new RegistrationResponseDto(savedUser.getUsername(), true);
  }
}
