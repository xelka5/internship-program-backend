package com.tusofia.internshipprogram.dto.user;

import com.tusofia.internshipprogram.dto.BaseResponseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UploadImageResponseDto extends BaseResponseDto {

  public UploadImageResponseDto(boolean isSuccess) {
    super(isSuccess);
  }
}
