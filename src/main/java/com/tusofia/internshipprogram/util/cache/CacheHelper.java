package com.tusofia.internshipprogram.util.cache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.stereotype.Component;

@Component
public class CacheHelper {

  private static final String REGISTER_CONFIRMATION_CACHE = "registerConfirmation";
  private static final String FORGOT_PASSWORD_CACHE = "forgotPassword";

  private CacheManager cacheManager;
  private Cache<String, String> registerConfirmationCache;
  private Cache<String, String> forgotPasswordCache;

  public CacheHelper() {
    cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build();
    cacheManager.init();

    registerConfirmationCache = cacheManager.createCache(REGISTER_CONFIRMATION_CACHE, CacheConfigurationBuilder
        .newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(10)));

    forgotPasswordCache = cacheManager.createCache(FORGOT_PASSWORD_CACHE, CacheConfigurationBuilder
        .newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(10)));
  }

  public Cache<String, String> getRegisterConfirmationCache() {
    return cacheManager.getCache(REGISTER_CONFIRMATION_CACHE, String.class, String.class);
  }

  public Cache<String, String> getForgotPasswordCache() {
    return cacheManager.getCache(FORGOT_PASSWORD_CACHE, String.class, String.class);
  }

}
