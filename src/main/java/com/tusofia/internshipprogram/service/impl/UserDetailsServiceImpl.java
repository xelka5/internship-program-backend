package com.tusofia.internshipprogram.service.impl;

import com.tusofia.internshipprogram.entity.user.User;
import com.tusofia.internshipprogram.exception.EntityNotFoundException;
import com.tusofia.internshipprogram.repository.UserRepository;
import com.tusofia.internshipprogram.service.domain.CustomUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private UserRepository userRepository;

  @Autowired
  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(username).orElseThrow(() -> new EntityNotFoundException("User not found: " + username));

    return new CustomUserPrincipal(user.getEmail(), user.getPassword(), user.getUserStatus(),
            Collections.singletonList(user.getRole()), user.getEmail());
  }
}
