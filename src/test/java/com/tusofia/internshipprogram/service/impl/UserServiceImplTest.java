package com.tusofia.internshipprogram.service.impl;

import com.tusofia.internshipprogram.config.ApplicationConfig;
import com.tusofia.internshipprogram.dto.user.UserDetailsDto;
import com.tusofia.internshipprogram.dto.user.UserDetailsResponseDto;
import com.tusofia.internshipprogram.entity.user.InternDetails;
import com.tusofia.internshipprogram.entity.user.User;
import com.tusofia.internshipprogram.enumeration.UserRole;
import com.tusofia.internshipprogram.enumeration.UserStatus;
import com.tusofia.internshipprogram.mapper.UserMapper;
import com.tusofia.internshipprogram.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

  private static final String TEST_USERNAME = "TestUsername";
  private static final String TEST_PASSWORD = "TestPassword";
  private static final String TEST_EMAIL = "test@email.com";
  private static final String TEST_PROFILE_IMAGE = "testProfileImage";

  private PasswordEncoder passwordEncoder;

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserMapper userMapper;

  @Mock
  private ApplicationConfig applicationConfig;

  @InjectMocks
  private UserServiceImpl userService;

  @Before
  public void init() {
    passwordEncoder = new BCryptPasswordEncoder();
  }

  @Test
  public void registerNewUserSuccessTest() {
    // GIVEN
    User newUser = createNewUser();
    when(userMapper.userDetailsDtoToUser(any(UserDetailsDto.class))).thenReturn(newUser);
    when(userRepository.save(any(User.class))).thenReturn(newUser);

    // WHEN
    UserDetailsResponseDto userDetailsResponseDto = userService.registerNewUser(new UserDetailsDto());

    // THEN
    assertTrue(userDetailsResponseDto.isSuccess());
    assertEquals(TEST_EMAIL, userDetailsResponseDto.getEmail());
  }


  private User createNewUser() {
    return new User(TEST_USERNAME, passwordEncoder.encode(TEST_PASSWORD),
            TEST_EMAIL, UserStatus.ACTIVE, true, UserRole.INTERN, TEST_PROFILE_IMAGE, new InternDetails(), null);
  }

}
