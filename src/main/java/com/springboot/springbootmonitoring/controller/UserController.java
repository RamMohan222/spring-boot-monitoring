package com.springboot.springbootmonitoring.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springboot.springbootmonitoring.annotation.SessionId;
import com.springboot.springbootmonitoring.annotation.TraceException;
import com.springboot.springbootmonitoring.annotation.TraceRequest;
import com.springboot.springbootmonitoring.model.TokenInfo;
import com.springboot.springbootmonitoring.model.UserModel;
import com.springboot.springbootmonitoring.service.IUserService;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
	private final Logger LOG = LoggerFactory.getLogger(UserController.class);

	private IUserService service;

	public UserController(IUserService service) {
		this.service = service;
	}

	@GetMapping
	@TraceRequest(label = "UserModel#list")
	@TraceException(value = "UserModel#list", tags = { "users", "v0" })
	public ResponseEntity<List<UserModel>> findAll() {
		List<UserModel> users = service.findAll();
		return ResponseEntity.ok(users);
	}

	@GetMapping("/{userId}")
	@TraceRequest(label = "UserModel#list", tags = { "user" })
	@TraceException(value = "UserModel#findById")
	public ResponseEntity<UserModel> findById(@PathVariable(required = true) UUID userId,
			@RequestParam(required = false, defaultValue = "default_name") String username, 
			@SessionId UUID sessionId,
			TokenInfo info) {

		LOG.info("info = {}", info);
		LOG.info("userId = {}", userId);
		LOG.info("username = {}", username);
		LOG.info("sessionId = {}", sessionId);
		
		UserModel user = service.findById(userId);
		return ResponseEntity.ok(user);
	}

	@PostMapping
	@TraceRequest(label = "UserModel#save", tags = { "user" })
	@TraceException(value = "UserModel#save")
	public ResponseEntity<UserModel> save(@RequestBody UserModel user) {
		user = service.save(user);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}").buildAndExpand(user.getId())
				.toUri();
		return ResponseEntity.created(location).body(user);
	}
}
