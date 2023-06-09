package com.example.springdemo.controller;

import com.example.springdemo.config.JwtUtil;
import com.example.springdemo.constants.ImgurConstants;
import com.example.springdemo.dto.LoginDTO;
import com.example.springdemo.dto.LoginResposne;
import com.example.springdemo.dto.UserDTO;
import com.example.springdemo.model.Image;
import com.example.springdemo.model.User;
import com.example.springdemo.service.UserService;
/*import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

//@Api(value = "CRUD Rest APIs for User")
@RestController
@RequestMapping("/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtUtil jwtUtil;

	private ImgurConstants imgurConstants;

	//@ApiOperation(value = "Save user Info")
	@PostMapping("/save")
	public String saveUser(@RequestBody UserDTO userdto){
		logger.info("User details: "+userdto);
		return userService.saveUserInfo(userdto);
	}

	@GetMapping("/{userId}")
	public User getUser(@PathVariable String userId){
		return userService.getUserById(userId);
	}

	@PostMapping(value="/login",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO){
		logger.info("User details: "+loginDTO);
		LoginResposne loginResponse = userService.loginUser(loginDTO);
		logger.info("response: "+loginResponse);
		return ResponseEntity.ok(loginResponse);
	}

	@PostMapping("/{userName}/uploadImage")
	public String uploadImage(@RequestHeader(value ="authorization",defaultValue="") String auth ,@RequestParam("image") MultipartFile file, @PathVariable String userName) throws Exception {

		String usernameFromToken = jwtUtil.getUserNameFromToken(auth);
		if(null!=usernameFromToken) {
			jwtUtil.verifyJwt(auth);
		return userService.uploadImage(file,userName);
		}
		return null;
	}

	@GetMapping("/{userName}/viewImage/{imageId}")
	public ResponseEntity<byte[]> downloadImage(@RequestHeader(value ="authorization",defaultValue="") String auth ,@PathVariable String userName, @PathVariable Integer imageId) throws Exception {
		
		String usernameFromToken = jwtUtil.getUserNameFromToken(auth);
		if(null!=usernameFromToken) {
			jwtUtil.verifyJwt(auth);
		String uri = imgurConstants.URI+userName+"/image/"+imageId;
		RestTemplate restTemplate = new RestTemplate();

		Image image = restTemplate.getForObject(uri,Image.class);
		byte[] imageData= image.getImageData();
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/gif")).body(imageData);
		}
		return new ResponseEntity<byte[]>(HttpStatus.OK);
	}

	//@ApiOperation(value = "Delete Image")
	@DeleteMapping("/{userName}/{imageId}")
	public ResponseEntity<String> deletePost(@RequestHeader(value ="authorization",defaultValue="") String auth ,@PathVariable String userName, @PathVariable Integer imageId) throws Exception{
		
		
		String usernameFromToken = jwtUtil.getUserNameFromToken(auth);
		if(null!=usernameFromToken) {
			jwtUtil.verifyJwt(auth);
		String uri = imgurConstants.URI+userName+"/image/"+imageId;
		Image image = new Image();
		image.setImageId(imageId);
		image.setUserName(userName);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(uri, image);
		return new ResponseEntity<>("Image deleted successfully.", HttpStatus.OK);
		}
		return new ResponseEntity<>("Error while deleting..",HttpStatus.OK);
	}

}
