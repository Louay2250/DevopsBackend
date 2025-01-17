package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.JwtProvider;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService {
	@Autowired
	private UserRepository userRepository ;
	@Autowired
	private JwtProvider jwtProvider;

	@Override
	public User findUserByJwt(String jwt) throws Exception  {
		
		String email = jwtProvider.getEmailFromJwtToken(jwt);
		
		if(email==null) {
			throw new Exception("provide a valid jwt token...");
			
		}
		User user=userRepository.findByEmail(email);
		if(user==null) {
			throw new Exception("user not found with email"+email);
			
		}
		return user;
	}
	@Override
	public User findUserById(Long userId) throws Exception {
		Optional<User> opt=userRepository.findById(userId);
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new Exception("User not found with id"+userId);
		
	}
	

}
