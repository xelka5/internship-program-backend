package com.tusofia.internshipprogram.util.authentication;

import com.tusofia.internshipprogram.enumeration.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.tusofia.internshipprogram.config.OAuth2AuthServerConfig.USER_ALLOWED_LABEL;
import static com.tusofia.internshipprogram.config.OAuth2AuthServerConfig.USER_STATUS_LABEL;

@Component
public class AuthenticationUtils {

  private static final String ACTIVE_USER_STATUS = "ACTIVE";
  private static final String ANONYMOUS_USER_STATUS = "ANONYMOUS";
  private static final String USER_ALLOWED = "true";

  @SuppressWarnings (value="unchecked")
  public static String extractClaimFromAuthDetails(Authentication authentication, String claimLabel) {
    Object authenticationDetails = authentication.getDetails();

    if(authenticationDetails instanceof OAuth2AuthenticationDetails) {
      OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)authentication.getDetails();
      Map<String, Object> decodedDetails = (LinkedHashMap<String, Object>)details.getDecodedDetails();

      return String.valueOf(decodedDetails.get(claimLabel));
    }

    return ANONYMOUS_USER_STATUS;
  }

  public boolean checkAdmin(Authentication authentication, HttpServletRequest request) {
    if(authentication.getAuthorities() == null) {
      return false;
    }

    boolean isAdmin = authentication.getAuthorities().toString().contains(UserRole.ADMIN.getAuthority());

    return isUserActiveAndAllowed(authentication, request) && isAdmin;
  }

  public boolean checkEmployer(Authentication authentication, HttpServletRequest request) {
    if(authentication.getAuthorities() == null) {
      return false;
    }

    boolean isEmployer = authentication.getAuthorities().toString().contains(UserRole.EMPLOYER.getAuthority());

    return isUserActiveAndAllowed(authentication, request) && isEmployer;
  }

  public static boolean isUserActiveAndAllowed(Authentication authentication, HttpServletRequest request) {
    return ACTIVE_USER_STATUS.equals(AuthenticationUtils.extractClaimFromAuthDetails(authentication, USER_STATUS_LABEL))
            && USER_ALLOWED.equals(AuthenticationUtils.extractClaimFromAuthDetails(authentication, USER_ALLOWED_LABEL));
  }
}
