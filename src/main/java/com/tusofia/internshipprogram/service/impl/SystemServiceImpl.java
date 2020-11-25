package com.tusofia.internshipprogram.service.impl;

import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.entity.user.User;
import com.tusofia.internshipprogram.enumeration.UserStatus;
import com.tusofia.internshipprogram.exception.EntityNotFoundException;
import com.tusofia.internshipprogram.repository.UserRepository;
import com.tusofia.internshipprogram.service.SystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.tusofia.internshipprogram.util.GlobalConstants.USER_NOT_FOUND_MESSAGE;

@Service
@Slf4j
public class SystemServiceImpl implements SystemService {

  private static final String RISK_ACTIVATION_MESSAGE = "User {} is activated by the system endpoint";

  private UserRepository userRepository;

  @Autowired
  public SystemServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public BaseResponseDto activateUser(String userEmail) {
    User savedUser = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND_MESSAGE));

    LOGGER.warn(RISK_ACTIVATION_MESSAGE, userEmail);

    savedUser.setUserStatus(UserStatus.ACTIVE);

    userRepository.save(savedUser);

    return new BaseResponseDto(true);
  }
}
