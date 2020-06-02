package com.tusofia.internshipprogram.dto.emailCheck;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailCheckResponseDto {

  private boolean emailExists;
}
