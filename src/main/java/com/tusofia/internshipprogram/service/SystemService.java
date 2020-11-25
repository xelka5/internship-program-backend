package com.tusofia.internshipprogram.service;

import com.tusofia.internshipprogram.dto.BaseResponseDto;

public interface SystemService {

  BaseResponseDto activateUser(String userEmail);
}
