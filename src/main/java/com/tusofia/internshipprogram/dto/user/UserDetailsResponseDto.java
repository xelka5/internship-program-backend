package com.tusofia.internshipprogram.dto.user;

import com.tusofia.internshipprogram.dto.BaseResponseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDetailsResponseDto extends BaseResponseDto {

  private String email;

  public UserDetailsResponseDto(String email, boolean isSuccess) {
    super(isSuccess);
    this.email = email;
  }
}
