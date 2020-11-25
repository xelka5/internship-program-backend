package com.tusofia.internshipprogram.util.cache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.stereotype.Component;

/**
 * In memory cache used for short term verifications like registration or forgot password confirmation.
 *
 * @author DCvetkov
 * @since 2020
 */
@Component
public class CacheHelper {

  private static final String REGISTER_CONFIRMATION_CACHE = "registerConfirmation";
  private static final String FORGOT_PASSWORD_CACHE = "forgotPassword";

  private CacheManager cacheManager;

  /**
   * Creating cache instance and registering two caches for
   * registration confirmation and forgot password functionality
   */
  public CacheHelper() {
    cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build();
    cacheManager.init();

    cacheManager.createCache(REGISTER_CONFIRMATION_CACHE, CacheConfigurationBuilder
            .newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(10)));

    cacheManager.createCache(FORGOT_PASSWORD_CACHE, CacheConfigurationBuilder
            .newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(10)));
  }

  public Cache<String, String> getRegisterConfirmationCache() {
    return cacheManager.getCache(REGISTER_CONFIRMATION_CACHE, String.class, String.class);
  }

  public Cache<String, String> getForgotPasswordCache() {
    return cacheManager.getCache(FORGOT_PASSWORD_CACHE, String.class, String.class);
  }

}
