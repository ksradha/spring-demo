package com.example.springdemo.service;

import com.example.springdemo.dto.LoginDTO;
import com.example.springdemo.dto.LoginResposne;
import com.example.springdemo.dto.UserDTO;
import com.example.springdemo.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

	String saveUserInfo(UserDTO userdto);

	LoginResposne loginUser(LoginDTO loginDTO);

	User getUserById(String userId);

	User getUserByName(String userName);

	String uploadImage(MultipartFile file, String userName) throws IOException;

	byte[] viewImage(String userName, String fileName) throws Exception;
}
