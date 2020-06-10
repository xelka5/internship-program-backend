package com.tusofia.internshipprogram.util.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.LinkedHashMap;
import java.util.Map;

public class AuthenticationUtils {

  @SuppressWarnings (value="unchecked")
  public static String extractClaimFromAuthDetails(Authentication authentication, String claimLabel) {
    OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)authentication.getDetails();
    Map<String, Object> decodedDetails = (LinkedHashMap<String, Object>)details.getDecodedDetails();

    return String.valueOf(decodedDetails.get(claimLabel));
  }
}
