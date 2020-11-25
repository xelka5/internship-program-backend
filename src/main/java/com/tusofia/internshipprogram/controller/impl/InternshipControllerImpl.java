package com.tusofia.internshipprogram.controller.impl;

import com.tusofia.internshipprogram.controller.InternshipController;
import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.dto.internship.AssignedInternDto;
import com.tusofia.internshipprogram.dto.internship.FinishInternshipDto;
import com.tusofia.internshipprogram.dto.internship.InternshipDto;
import com.tusofia.internshipprogram.dto.internship.InternshipExtendedDto;
import com.tusofia.internshipprogram.enumeration.InternshipStatus;
import com.tusofia.internshipprogram.service.InternshipService;
import com.tusofia.internshipprogram.util.authentication.AuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.tusofia.internshipprogram.config.OAuth2AuthServerConfig.USER_EMAIL_LABEL;

/**
 * Url mapping and implementation of {@link InternshipController}
 *
 * @author DCvetkov
 * @since 2020
 */
@RestController
@RequestMapping("/api/internships")
public class InternshipControllerImpl implements InternshipController {

  private InternshipService internshipService;

  @Autowired
  public InternshipControllerImpl(InternshipService internshipService) {
    this.internshipService = internshipService;
  }

  @GetMapping
  public List<InternshipExtendedDto> getAllInternshipsByStatus(@RequestParam("status") String status) {
    return internshipService.getAllInternshipsByStatus(InternshipStatus.valueOf(status));
  }

  @GetMapping("/search")
  public List<InternshipExtendedDto> searchActiveInternships(@RequestParam("searchTerm") String searchTerm) {
    return internshipService.searchActiveInternships(searchTerm);
  }

  @GetMapping("/assigned")
  public List<InternshipExtendedDto> getAssignedInternInternshipsByStatus(@RequestParam("status") String status,
                                                                          Authentication authentication) {

    String userEmail = AuthenticationUtils.extractClaimFromAuthDetails(authentication, USER_EMAIL_LABEL);

    return internshipService.getInternInternshipsByStatus(userEmail, InternshipStatus.valueOf(status));
  }

  @GetMapping("/employer")
  public List<InternshipDto> getEmployerInternshipsByStatus(@RequestParam("status") String status,
                                                            Authentication authentication) {

    String userEmail = AuthenticationUtils.extractClaimFromAuthDetails(authentication, USER_EMAIL_LABEL);

    return internshipService.getEmployerInternshipsByStatus(userEmail, InternshipStatus.valueOf(status));
  }

  @GetMapping("/employer/{trackingNumber}")
  public InternshipDto getInternshipByTrackingNumber(@PathVariable("trackingNumber") String trackingNumber,
                                                     Authentication authentication) {

    String userEmail = AuthenticationUtils.extractClaimFromAuthDetails(authentication, USER_EMAIL_LABEL);

    return internshipService.getInternshipByTrackingNumber(trackingNumber, userEmail);
  }

  @GetMapping("/employer/assigned/{trackingNumber}")
  public List<AssignedInternDto> getAssignedInterns(@PathVariable("trackingNumber") String trackingNumber,
                                                    Authentication authentication) {

    String userEmail = AuthenticationUtils.extractClaimFromAuthDetails(authentication, USER_EMAIL_LABEL);

    return internshipService.getAssignedInterns(trackingNumber, userEmail);
  }

  @PostMapping
  public BaseResponseDto addNewInternship(@Valid @RequestBody InternshipDto internshipDto,
                                          Authentication authentication) {

    String userEmail = AuthenticationUtils.extractClaimFromAuthDetails(authentication, USER_EMAIL_LABEL);

    return internshipService.addNewInternship(internshipDto, userEmail);
  }

  @PutMapping("/employer")
  public BaseResponseDto editInternship(@Valid @RequestBody InternshipDto internshipDto,
                                        Authentication authentication) {

    String userEmail = AuthenticationUtils.extractClaimFromAuthDetails(authentication, USER_EMAIL_LABEL);

    return internshipService.editInternship(internshipDto, userEmail);
  }

  @PutMapping("/employer/finish")
  public BaseResponseDto finishInternship(@Valid @RequestBody FinishInternshipDto finishInternshipDto,
                                          Authentication authentication) {

    String userEmail = AuthenticationUtils.extractClaimFromAuthDetails(authentication, USER_EMAIL_LABEL);

    return internshipService.finishInternship(finishInternshipDto, userEmail);
  }

  @DeleteMapping("employer/{trackingNumber}")
  public BaseResponseDto deleteInternship(@PathVariable("trackingNumber") String trackingNumber,
                                          Authentication authentication) {

    String userEmail = AuthenticationUtils.extractClaimFromAuthDetails(authentication, USER_EMAIL_LABEL);

    return internshipService.deleteInternship(trackingNumber, userEmail);
  }

}
