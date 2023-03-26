package com.springboot.springbootmonitoring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.springboot.springbootmonitoring.exception.RecordNotFoundException;
import com.springboot.springbootmonitoring.model.UserModel;

@Service
public class UserServiceImpl implements IUserService {

	private List<UserModel> users = new ArrayList<>();

	@Override
	public UserModel findById(UUID userId) {
		return users.stream().filter(o -> o.getId().equals(userId)).findFirst()
				.orElseThrow(() -> new RecordNotFoundException("Invalid user"));
	}

	@Override
	public UserModel save(UserModel user) {
		users.add(user);
		return user;
	}

	@Override
	public List<UserModel> findAll() {
		return users;
	}

}
