package com.example.springdemo.service.impl;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springdemo.dao.UserRepository;
import com.example.springdemo.model.User;


@Service
public class UserServiceImplBySpringSecurity implements UserDetailsService{
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImplBySpringSecurity.class);

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user1 = userRepository.findByUserName(userName);
		
		return new org.springframework.security.core.userdetails.User(user1.getUserName(),user1.getPassword(),new ArrayList<>());	
	}

	
}
