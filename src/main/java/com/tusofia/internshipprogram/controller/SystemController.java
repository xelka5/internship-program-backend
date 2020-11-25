package com.tusofia.internshipprogram.controller;

import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.dto.system.ActivateUserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Api description of system operations controller.
 *
 * @author DCvetkov
 * @since 2020
 */
@Api(tags = {"System Endpoints"}, description = "System operations")
public interface SystemController {

  @ApiOperation(value = "Activate User",
          notes = "Manually updates user status to ACTIVE by given user email")
  BaseResponseDto activateUser(ActivateUserDto activateUserDto);
}
