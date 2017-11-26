package com.ss.project.service;

import org.springframework.http.ResponseEntity;

import com.ss.project.dto.UserDTO;

public interface IUserService {

	public ResponseEntity<?> createUser(UserDTO userDto);

	public ResponseEntity<?> updateUser(UserDTO userDto);

	public ResponseEntity<?> deleteUser(UserDTO userDto);

}
