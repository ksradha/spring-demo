package com.example.springdemo.service.impl;

import com.example.springdemo.config.JwtUtil;
import com.example.springdemo.dao.ImageRepository;
import com.example.springdemo.dao.UserRepository;
import com.example.springdemo.dto.LoginDTO;
import com.example.springdemo.dto.LoginResposne;
import com.example.springdemo.dto.UserDTO;
import com.example.springdemo.exception.ImageException;
import com.example.springdemo.model.Image;
import com.example.springdemo.model.User;
import com.example.springdemo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder pwdEncoder;

	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public String saveUserInfo(UserDTO userdto) {

		String encodedPassword=this.pwdEncoder.encode(userdto.getPassword());
		//logger.info("encoded password is :  "+encodedPassword);
		User user = new User(
				userdto.getUserId(),
				userdto.getUserName(),
				encodedPassword
				);
		userRepository.save(user);
		return "User"+user.getUserName()+" saved successfully..";

	}

	@Override
	public LoginResposne loginUser(LoginDTO loginDTO) {
		User user1 = userRepository.findByUserName(loginDTO.getUserName());
		logger.info("user info : "+user1);
		if(user1!=null){
			String password = loginDTO.getPassword();
			String encodedPassword = user1.getPassword();
			Boolean isPwdCorrect = pwdEncoder.matches(password,encodedPassword);
			logger.info("isPwdCorrect : "+isPwdCorrect);
			if(isPwdCorrect){
				Optional<User> user = userRepository.findByUserNameAndPassword(loginDTO.getUserName(),encodedPassword);
				if(user.isPresent()) {
					logger.info("user is present in db... :)");
					String token = jwtUtil.generateJwt(new org.springframework.security.core.userdetails.User(user1.getUserName(), encodedPassword, new ArrayList<>()));
					Map<String,Object> data = new HashMap<>();
					data.put("accessToken", token);
					return new LoginResposne(data, true);
				}else{
					return new LoginResposne("Login Failed",false);
				}
			}else{
				return new LoginResposne("Password not matching", false);
			}
		}else{
			return new LoginResposne("userName not present",false);
		}
	}

	@Override
	public User getUserById(String userId) {
		return userRepository.findByUserId(Integer.parseInt(userId));
	}

	@Override
	public User getUserByName(String userName) {
		return userRepository.findByUserName(userName);
	}

	private boolean isUserPresent(String userName){
		User user = getUserByName(userName);
		if(user!=null)
			return true;
		return false;
	}

	@Override
	public String uploadImage(MultipartFile file, String userName) throws IOException {
		if(isUserPresent(userName)) {
			Image image = new Image();
			image.setImageName(file.getOriginalFilename());
			image.setImageType(file.getContentType());
			image.setImageData(file.getBytes());
			image.setUserName(userName);

			imageRepository.save(image);
			return "Image uploaded successfully..";
		}else{
			return "Image upload failed";
		}

	}

	@Override
	public byte[] viewImage(String userName, String fileName) throws Exception {
		try {
			if (isUserPresent(userName)) {
				Optional<Image> imageData = imageRepository.findByImageNameAndUserName(fileName, userName);
				return imageData.get().getImageData();
			}
		}catch (Exception e){
			throw new ImageException("Image not found");
		}
		return null;
	}
}
