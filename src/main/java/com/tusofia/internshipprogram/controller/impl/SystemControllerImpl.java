package com.tusofia.internshipprogram.controller.impl;

import com.tusofia.internshipprogram.controller.SystemController;
import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.dto.system.ActivateUserDto;
import com.tusofia.internshipprogram.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Url mapping and implementation of {@link SystemController}
 * <p>
 * Should not be used in PROD environment!!!
 *
 * @author DCvetkov
 * @since 2020
 */
@RestController
@RequestMapping("/api/system")
@Profile("!prod") // don't change this line!!!
public class SystemControllerImpl implements SystemController {

  private SystemService systemService;

  @Autowired
  public SystemControllerImpl(SystemService systemService) {
    this.systemService = systemService;
  }

  /**
   * Since this is a very risky operation, do not perform on real users!
   *
   * @param activateUserDto - object containing email of user to be activated
   * @return {@link BaseResponseDto}
   */
  @PostMapping("/activate-user")
  public BaseResponseDto activateUser(@Valid @RequestBody ActivateUserDto activateUserDto) {
    systemService.activateUser(activateUserDto.getUserEmail());

    return new BaseResponseDto(true);
  }
}
