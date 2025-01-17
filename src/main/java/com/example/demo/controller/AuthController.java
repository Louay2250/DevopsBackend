package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.JwtProvider;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.LoginRequest;
import com.example.demo.response.AuthRespose;
import com.example.demo.service.CustomeUserDetailService;

@RestController
@RequestMapping("/auth") 
public class AuthController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CustomeUserDetailService customerUserDetails;
	@Autowired
	private JwtProvider jwtProvider ;
	@Autowired
	private PasswordEncoder passwordEncoder ;
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/signup")
	public  AuthRespose createUser(@RequestBody User user) throws Exception{
		
		String email=user.getEmail();
		String password=user.getPassword();
		String fullName=user.getFullName();
		
		User isExistEmail = userRepository.findByEmail(email);
		if(isExistEmail!=null) {
			
			throw new Exception("Email is already used by another account");
		}
		User createdUser=new User();
		createdUser.setEmail(email);
		createdUser.setPassword(passwordEncoder.encode(password));
		createdUser.setFullName(fullName);
		
		
		User savedUser=userRepository.save(createdUser);
		
		Authentication authentication =new UsernamePasswordAuthenticationToken(createdUser, password);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token=jwtProvider.generateToken(authentication);
		
		AuthRespose res= new AuthRespose();
		res.setJwt(token);
		res.setMessage("signup successfuly");
		
		return res;
		
		
		
	}
	@PostMapping("/signin")
	public AuthRespose signinHandler(@RequestBody LoginRequest loginRequest) {
		String username =loginRequest.getEmail();
		String password=loginRequest.getPassword();
		
		Authentication authentication=authenticate(username,password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token=jwtProvider.generateToken(authentication);
		
		AuthRespose res= new AuthRespose();
		res.setJwt(token);
		res.setMessage("signin successfuly");
		
		return res;
		
		
		
	}

	private Authentication authenticate(String username, String password) {
		UserDetails userDetails=customerUserDetails.loadUserByUsername(username);
		if(userDetails==null) {
			throw new BadCredentialsException("user not found");
		}
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("invalid password");
		}
		
		return new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
	}

}
