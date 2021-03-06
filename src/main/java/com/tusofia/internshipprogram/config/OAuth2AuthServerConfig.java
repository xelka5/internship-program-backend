package com.tusofia.internshipprogram.config;

import com.tusofia.internshipprogram.service.domain.CustomUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tusofia.internshipprogram.util.GlobalConstants.READ_SCOPE;
import static com.tusofia.internshipprogram.util.GlobalConstants.WRITE_SCOPE;

/**
 * Basic authentication server configuration with oauth2 protocol.
 *
 * @author DCvetkov
 * @since 2020
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthServerConfig extends AuthorizationServerConfigurerAdapter {

  public static final String USER_EMAIL_LABEL = "user_email";
  public static final String USER_STATUS_LABEL = "user_status";
  public static final String USER_ALLOWED_LABEL = "user_allowed";

  @Value("${oauthData.clientId}")
  private String clientId;

  @Value("${oauthData.clientSecret}")
  private String clientSecret;

  @Value("${oauthData.jwtSigningKey}")
  private String jwtSigningKey;

  @Value("${oauthData.accessTokenValidity}")
  private int accessTokenValiditySeconds;

  @Value("${oauthData.authorizedGrantTypes:password,refresh_token}")
  private String[] authorizedGrantTypes;

  @Value("${oauthData.refreshTokenValidity}")
  private int refreshTokenValiditySeconds;

  @Value("${cors.allowedOrigin}")
  private String allowedOrigin;

  private final PasswordEncoder passwordEncoder;
  private final UserDetailsService userDetailsService;
  private final AuthenticationManager authenticationManager;

  @Autowired
  public OAuth2AuthServerConfig(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
    this.authenticationManager = authenticationManager;
    this.passwordEncoder = passwordEncoder;
    this.userDetailsService = userDetailsService;
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.inMemory()
            .withClient(clientId)
            .secret(passwordEncoder.encode(clientSecret))
            .accessTokenValiditySeconds(accessTokenValiditySeconds)
            .refreshTokenValiditySeconds(refreshTokenValiditySeconds)
            .authorizedGrantTypes(authorizedGrantTypes)
            .scopes(READ_SCOPE, WRITE_SCOPE);
  }

  @Override
  public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
    TokenEnhancerChain chain = new TokenEnhancerChain();
    chain.setTokenEnhancers(List.of(tokenEnhancer(), accessTokenConverter()));
    endpoints.authenticationManager(authenticationManager)
            .userDetailsService(userDetailsService)
            .tokenStore(tokenStore())
            .tokenEnhancer(chain)
            .accessTokenConverter(accessTokenConverter());
  }

  @Bean
  public JwtTokenStore tokenStore() {
    return new JwtTokenStore(accessTokenConverter());
  }

  @Bean
  public JwtAccessTokenConverter accessTokenConverter() {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    converter.setSigningKey(jwtSigningKey);
    converter.setVerifierKey(jwtSigningKey);
    converter.setAccessTokenConverter(authExtractor());
    return converter;
  }

  private TokenEnhancer tokenEnhancer() {
    return (accessToken, authentication) -> {
      if (authentication != null && authentication.getPrincipal() instanceof CustomUserPrincipal) {
        CustomUserPrincipal userPrincipal = (CustomUserPrincipal) authentication.getPrincipal();
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put(USER_EMAIL_LABEL, userPrincipal.getEmail());
        additionalInfo.put(USER_STATUS_LABEL, userPrincipal.getUserStatus());
        additionalInfo.put(USER_ALLOWED_LABEL, userPrincipal.getUserAllowed());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
      }
      return accessToken;
    };
  }

  @Bean
  public DefaultAccessTokenConverter authExtractor() {
    return new DefaultAccessTokenConverter() {
      @Override
      public OAuth2Authentication extractAuthentication(Map<String, ?> claims) {
        OAuth2Authentication authentication = super.extractAuthentication(claims);
        authentication.setDetails(claims);
        return authentication;
      }
    };
  }

  @Bean
  public FilterRegistrationBean corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", getCorsConfiguration());

    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean<>(new CorsFilter(source));
    filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);

    return filterRegistrationBean;
  }

  private CorsConfiguration getCorsConfiguration() {
    CorsConfiguration corsConfig = new CorsConfiguration();
    corsConfig.setAllowCredentials(true);
    corsConfig.addAllowedOrigin(allowedOrigin);
    corsConfig.addAllowedHeader("*");
    corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

    return corsConfig;
  }

}
