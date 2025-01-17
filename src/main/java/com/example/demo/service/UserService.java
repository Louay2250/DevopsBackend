package com.example.demo.service;

import com.example.demo.model.User;

public interface UserService {
	public User findUserByJwt(String jwt) throws Exception ;

	public User findUserById(Long userId) throws Exception;
	





}
