package com.example.springdemo.dao;


import com.example.springdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findByUserId(int userId);

    User findByUserName(String userName);

    Optional<User> findByUserNameAndPassword(String userName, String encodedPassword);
}
