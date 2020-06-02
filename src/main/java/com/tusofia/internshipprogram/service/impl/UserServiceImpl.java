package com.tusofia.internshipprogram.service.impl;

import com.tusofia.internshipprogram.dto.emailCheck.EmailCheckRequestDto;
import com.tusofia.internshipprogram.dto.emailCheck.EmailCheckResponseDto;
import com.tusofia.internshipprogram.dto.registration.RegistrationResponseDto;
import com.tusofia.internshipprogram.dto.registration.UserDetailsDto;
import com.tusofia.internshipprogram.entity.UserEntity;
import com.tusofia.internshipprogram.exception.UserNotFoundException;
import com.tusofia.internshipprogram.mapper.UserMapper;
import com.tusofia.internshipprogram.repository.UserRepository;
import com.tusofia.internshipprogram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private UserMapper userMapper;

  @Autowired
  public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  @Override
  public RegistrationResponseDto registerNewUser(UserDetailsDto newUserDetails) {
    UserEntity newUser = userMapper.userDetailsDtoToUserEntity(newUserDetails);

    UserEntity savedUser = userRepository.save(newUser);

    return new RegistrationResponseDto(savedUser.getUsername(), true);
  }

  @Override
  public EmailCheckResponseDto checkEmail(EmailCheckRequestDto emailCheckRequest) {
    Optional<UserEntity> userEntityOptional = userRepository.findByEmail(emailCheckRequest.getEmailToCheck());

    return new EmailCheckResponseDto(userEntityOptional.isPresent());
  }

  @Override
  public UserDetailsDto getUserDetails(Long userId) {

    UserEntity savedUser = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User does not exist"));

    UserDetailsDto userDetailsDto = userMapper.userEntityToUserDetailsDto(savedUser);

    switch(savedUser.getRole()) {
      case INTERN:
        userDetailsDto.setUserDetails(savedUser.getInternDetails());
        break;
      case EMPLOYER:
        userDetailsDto.setUserDetails(savedUser.getEmployerDetails());
        break;
      default:
        break;
    }

    return userDetailsDto;
  }
}
