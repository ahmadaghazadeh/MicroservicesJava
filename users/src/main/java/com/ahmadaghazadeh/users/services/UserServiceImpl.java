package com.ahmadaghazadeh.users.services;

import com.ahmadaghazadeh.users.data.UserEntity;
import com.ahmadaghazadeh.users.data.UserRepository;
import com.ahmadaghazadeh.users.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    UserRepository userRepository;

    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,BCryptPasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }
    public UserDto CreateUser(UserDto userDetails) {

        userDetails.setUserId(UUID.randomUUID().toString());
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDetails, UserEntity.class);
        userEntity.setEncryptedPassword(passwordEncoder.encode(userDetails.getPassword()));
        userRepository.save(userEntity);
        return mapper.map(userEntity,UserDto.class);
    }

    @Override
    public UserDto findByUserName(String email) {
        UserEntity userEntity=userRepository.findByEmail(email);
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(userEntity, UserDto.class);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity=userRepository.findByUserName(username);
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(userEntity, UserDetails.class);
    }
}
