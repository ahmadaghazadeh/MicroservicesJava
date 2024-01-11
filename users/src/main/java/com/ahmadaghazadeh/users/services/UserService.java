package com.ahmadaghazadeh.users.services;

import com.ahmadaghazadeh.users.shared.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDto CreateUser(UserDto userDetails);

    UserDto findByUserName(String email);

    UserDto getUserByEmail(String userId);
}
