package com.tusofia.internshipprogram.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @GetMapping
  public String getUserInfo() {
    return "UserInfo1";
  }
}
