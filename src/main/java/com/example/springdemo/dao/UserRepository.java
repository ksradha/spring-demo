package com.example.springdemo.dao;


import com.example.springdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

	User findByUserId(int userId);

	User findByUserName(String userName);

	Optional<User> findByUserNameAndPassword(String userName, String encodedPassword);
}
