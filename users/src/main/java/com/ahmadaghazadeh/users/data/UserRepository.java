package com.ahmadaghazadeh.users.data;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

@EnableJpaRepositories
public interface UserRepository extends CrudRepository<UserEntity,Long> {
    UserEntity findByEmail(String username);

    UserEntity findByUserName(String username);
}
