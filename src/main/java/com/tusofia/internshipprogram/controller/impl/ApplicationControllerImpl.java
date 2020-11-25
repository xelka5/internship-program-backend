package com.tusofia.internshipprogram.controller.impl;

import com.tusofia.internshipprogram.controller.ApplicationController;
import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.dto.application.ApplicationDetailsDto;
import com.tusofia.internshipprogram.dto.application.ApplicationResponseDto;
import com.tusofia.internshipprogram.dto.application.InternshipApplicationDto;
import com.tusofia.internshipprogram.dto.application.PendingApplicationDto;
import com.tusofia.internshipprogram.service.ApplicationService;
import com.tusofia.internshipprogram.util.authentication.AuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.tusofia.internshipprogram.config.OAuth2AuthServerConfig.USER_EMAIL_LABEL;

/**
 * Url mapping and implementation of {@link ApplicationController}
 *
 * @author DCvetkov
 * @since 2020
 */
@RestController
@RequestMapping("/api/applications")
public class ApplicationControllerImpl implements ApplicationController {

  private ApplicationService applicationService;

  @Autowired
  public ApplicationControllerImpl(ApplicationService applicationService) {
    this.applicationService = applicationService;
  }

  @GetMapping
  public List<ApplicationDetailsDto> getAllInternApplications(Authentication authentication) {
    String userEmail = AuthenticationUtils.extractClaimFromAuthDetails(authentication, USER_EMAIL_LABEL);

    return applicationService.getAllInternApplications(userEmail);
  }

  @GetMapping("/employer/pending")
  public List<PendingApplicationDto> getAllPendingApplications(Authentication authentication) {
    String userEmail = AuthenticationUtils.extractClaimFromAuthDetails(authentication, USER_EMAIL_LABEL);

    return applicationService.getAllPendingApplications(userEmail);
  }

  @PostMapping
  public BaseResponseDto addNewApplication(@Valid @RequestBody InternshipApplicationDto internshipApplicationDto, Authentication authentication) {
    String userEmail = AuthenticationUtils.extractClaimFromAuthDetails(authentication, USER_EMAIL_LABEL);

    return applicationService.addNewApplication(internshipApplicationDto, userEmail);
  }

  @PutMapping("/employer")
  public BaseResponseDto updateApplicationResponse(@Valid @RequestBody ApplicationResponseDto applicationResponseDto, Authentication authentication) {
    String userEmail = AuthenticationUtils.extractClaimFromAuthDetails(authentication, USER_EMAIL_LABEL);

    return applicationService.updateApplicationResponse(applicationResponseDto, userEmail);
  }
}
