package com.tusofia.internshipprogram.mapper;

import com.tusofia.internshipprogram.dto.user.UserDetailsDto;
import com.tusofia.internshipprogram.entity.user.User;
import org.codehaus.jackson.map.ObjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = { ObjectMapper.class,  })
public interface AdminMapper {

  List<UserDetailsDto> pendingUserListToUserDetailsDtoList(List<User> users);

  @Mapping(target = "account.email", source = "email")
  @Mapping(target = "account.username", source = "username")
  @Mapping(target = "account.password", ignore = true)
  @Mapping(target = "account.profileImageName", source = "profileImageName")
  @Mapping(target = "userDetails", source = "employerDetails")
  UserDetailsDto pendingUserToUserDetailsDto(User user);
}
