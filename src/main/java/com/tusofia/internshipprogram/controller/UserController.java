package com.tusofia.internshipprogram.controller;

import com.tusofia.internshipprogram.dto.emailCheck.EmailCheckRequestDto;
import com.tusofia.internshipprogram.dto.emailCheck.EmailCheckResponseDto;
import com.tusofia.internshipprogram.dto.registration.UserDetailsDto;
import com.tusofia.internshipprogram.dto.registration.RegistrationResponseDto;
import com.tusofia.internshipprogram.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public UserDetailsDto getUserDetails(@RequestParam Long userId) {
    return userService.getUserDetails(userId);
  }

  @PostMapping("/registration")
  public RegistrationResponseDto registerNewUser(@Valid @RequestBody UserDetailsDto registrationRequest) {
    return userService.registerNewUser(registrationRequest);
  }

  @PostMapping("/email-check")
  public EmailCheckResponseDto checkEmail(@Valid @RequestBody EmailCheckRequestDto emailCheckRequest) {
    return userService.checkEmail(emailCheckRequest);
  }
}
