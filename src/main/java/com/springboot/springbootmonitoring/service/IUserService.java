package com.springboot.springbootmonitoring.service;

import java.util.List;
import java.util.UUID;

import com.springboot.springbootmonitoring.model.UserModel;

public interface IUserService {
	public UserModel findById(UUID userId);
	public UserModel save(UserModel user);
	public List<UserModel> findAll();
}
