package com.tusofia.internshipprogram.controller;

import com.tusofia.internshipprogram.dto.RegistrationResponseDto;
import com.tusofia.internshipprogram.dto.UserDto;
import com.tusofia.internshipprogram.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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

  @PostMapping("/registration")
  public RegistrationResponseDto registerNewUser(@RequestBody UserDto userDto) {
    return userService.registerNewUser(userDto);
  }

  @GetMapping
  public List<String> getMe() {
    return Collections.singletonList("Passed!");
  }
}
