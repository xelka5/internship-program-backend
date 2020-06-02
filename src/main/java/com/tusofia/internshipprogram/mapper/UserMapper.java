package com.tusofia.internshipprogram.mapper;

import com.tusofia.internshipprogram.dto.registration.EmployerUserDetailsDto;
import com.tusofia.internshipprogram.dto.registration.InternUserDetailsDto;
import com.tusofia.internshipprogram.dto.registration.UserDetailsDto;
import com.tusofia.internshipprogram.entity.EmployerDetails;
import com.tusofia.internshipprogram.entity.InternDetails;
import com.tusofia.internshipprogram.entity.UserEntity;
import org.codehaus.jackson.map.ObjectMapper;
import org.mapstruct.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        imports = { ObjectMapper.class, BCryptPasswordEncoder.class })
public interface UserMapper {

  @Mapping(target = "account.email", source = "email")
  @Mapping(target = "account.username", source = "username")
  @Mapping(target = "account.password", ignore = true)
  UserDetailsDto userEntityToUserDetailsDto(UserEntity userEntity);

  @Mapping(target = "email", source = "account.email")
  @Mapping(target = "username", source = "account.username")
  @Mapping(target = "password", source = "account.password", qualifiedByName = "hashPassword")
  UserEntity userDetailsDtoToUserEntity(UserDetailsDto userDetailsDto);

  InternUserDetailsDto internDetailsToInternUserDetailsDto(InternDetails internDetails);

  InternDetails internUserDetailsDtoToInternDetails(InternUserDetailsDto internUserDetailsDto);

  EmployerUserDetailsDto employerDetailsToEmployerUserDetailsDto(EmployerDetails employerDetails);

  EmployerDetails employerUserDetailsDtoToEmployerDetails(EmployerUserDetailsDto internUserDetailsDto);

  @AfterMapping
  default void assignUserDetails(@MappingTarget UserEntity userEntity, UserDetailsDto userDetailsDto) {
    ObjectMapper objectMapper = new ObjectMapper();
    switch(userDetailsDto.getRole()) {
      case INTERN:
        InternUserDetailsDto internUserDetailsDto =
                objectMapper.convertValue(userDetailsDto.getUserDetails(), InternUserDetailsDto.class);
        InternDetails internDetails = internUserDetailsDtoToInternDetails(internUserDetailsDto);
        internDetails.setUser(userEntity);
        userEntity.setInternDetails(internDetails);
        break;
      case EMPLOYER:
        EmployerUserDetailsDto employerUserDetailsDto =
                objectMapper.convertValue(userDetailsDto.getUserDetails(), EmployerUserDetailsDto.class);
        EmployerDetails employerDetails = employerUserDetailsDtoToEmployerDetails(employerUserDetailsDto);
        employerDetails.setUser(userEntity);
        userEntity.setEmployerDetails(employerDetails);
        break;
      default:
        break;
    }
  }

  @Named(value = "hashPassword")
  default String hashPassword(String password) {
    return new BCryptPasswordEncoder().encode(password);
  }
}