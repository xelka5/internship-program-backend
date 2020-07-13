package com.tusofia.internshipprogram.util.authentication;

import com.tusofia.internshipprogram.enumeration.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.tusofia.internshipprogram.config.OAuth2AuthServerConfig.USER_STATUS_LABEL;

@Component
public class AuthenticationUtils {

  private static final String ACTIVE_USER_STATUS = "ACTIVE";

  @SuppressWarnings (value="unchecked")
  public static String extractClaimFromAuthDetails(Authentication authentication, String claimLabel) {
    OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)authentication.getDetails();
    Map<String, Object> decodedDetails = (LinkedHashMap<String, Object>)details.getDecodedDetails();

    return String.valueOf(decodedDetails.get(claimLabel));
  }

  public boolean checkAdmin(Authentication authentication, HttpServletRequest request) {
    return isUserActive(authentication) && authentication.getAuthorities().toString().contains(UserRole.ADMIN.getAuthority());
  }

  public boolean checkEmployer(Authentication authentication, HttpServletRequest request) {
    return isUserActive(authentication) && authentication.getAuthorities().toString().contains(UserRole.EMPLOYER.getAuthority());
  }

  private boolean isUserActive(Authentication authentication) {
    return AuthenticationUtils.extractClaimFromAuthDetails(authentication, USER_STATUS_LABEL).equals(ACTIVE_USER_STATUS);
  }
}
