package com.tusofia.internshipprogram.mapper;

import com.tusofia.internshipprogram.dto.user.EmployerUserDetailsDto;
import com.tusofia.internshipprogram.dto.user.InternUserDetailsDto;
import com.tusofia.internshipprogram.dto.user.UserDetailsDto;
import com.tusofia.internshipprogram.entity.user.EmployerDetails;
import com.tusofia.internshipprogram.entity.user.InternDetails;
import com.tusofia.internshipprogram.entity.user.User;
import com.tusofia.internshipprogram.enumeration.UserRole;
import com.tusofia.internshipprogram.enumeration.UserStatus;
import org.codehaus.jackson.map.ObjectMapper;
import org.mapstruct.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = { ObjectMapper.class, BCryptPasswordEncoder.class })
public interface UserMapper {

  @Mapping(target = "account.email", source = "email")
  @Mapping(target = "account.username", source = "username")
  @Mapping(target = "account.password", ignore = true)
  @Mapping(target = "account.profileImageName", source = "profileImageName")
  UserDetailsDto userToUserDetailsDto(User user);

  @Mapping(target = "email", source = "account.email")
  @Mapping(target = "username", source = "account.username")
  @Mapping(target = "password", source = "account.password", qualifiedByName = "hashPassword")
  @Mapping(target = "profileImageName", source = "account.profileImageName", defaultValue = "templates/default_user.png")
  User userDetailsDtoToUser(UserDetailsDto userDetailsDto);

  @Mapping(target = "username", source = "account.username")
  void updateUserDetails(UserDetailsDto updatedUserDetails, @MappingTarget User user);

  InternUserDetailsDto internDetailsToInternUserDetailsDto(InternDetails internDetails);

  @Mapping(target = "skills", source = "skills", qualifiedByName = "filterSkills")
  InternDetails internUserDetailsDtoToInternDetails(InternUserDetailsDto internUserDetailsDto);

  void updateInternUserDetails(InternUserDetailsDto internUpdateDetails, @MappingTarget InternDetails internDetails);

  EmployerUserDetailsDto employerDetailsToEmployerUserDetailsDto(EmployerDetails employerDetails);

  EmployerDetails employerUserDetailsDtoToEmployerDetails(EmployerUserDetailsDto internUserDetailsDto);

  void updateEmployerUserDetails(EmployerUserDetailsDto employerUpdateDetails, @MappingTarget EmployerDetails employerDetails);

  @AfterMapping
  default void assignUserDetails(@MappingTarget User user, UserDetailsDto userDetailsDto) {
    ObjectMapper objectMapper = new ObjectMapper();
    UserRole userRole = userDetailsDto.getRole();

    if(userRole != null) {
      switch(userRole) {
        case INTERN:
          InternUserDetailsDto internUserDetailsDto =
                  objectMapper.convertValue(userDetailsDto.getUserDetails(), InternUserDetailsDto.class);
          InternDetails internDetails = user.getInternDetails();

          if(internDetails != null) {
            updateInternUserDetails(internUserDetailsDto, user.getInternDetails());
          } else {
            user.setUserAllowed(Boolean.TRUE);
            internDetails = internUserDetailsDtoToInternDetails(internUserDetailsDto);
          }

          internDetails.setUser(user);
          user.setInternDetails(internDetails);
          break;
        case EMPLOYER:
          EmployerUserDetailsDto employerUserDetailsDto =
                  objectMapper.convertValue(userDetailsDto.getUserDetails(), EmployerUserDetailsDto.class);
          EmployerDetails employerDetails = user.getEmployerDetails();

          if(employerDetails != null) {
            updateEmployerUserDetails(employerUserDetailsDto, user.getEmployerDetails());
          } else {
            user.setUserAllowed(Boolean.FALSE);
            employerDetails = employerUserDetailsDtoToEmployerDetails(employerUserDetailsDto);
          }

          employerDetails.setUser(user);
          user.setEmployerDetails(employerDetails);
          break;
      }

      user.setUserStatus(UserStatus.PENDING_CONFIRMATION);
    }
  }

  @Named(value = "filterSkills")
  default List<String> filterSkills(List<String> skills) {
    return skills.stream().filter(skill -> !skill.isBlank()).collect(Collectors.toList());
  }

  @Named(value = "hashPassword")
  default String hashPassword(String password) {
    return new BCryptPasswordEncoder().encode(password);
  }
}