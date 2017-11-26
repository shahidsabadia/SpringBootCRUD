package com.ss.project.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ss.project.constant.AppConstant;
import com.ss.project.dto.UserDTO;
import com.ss.project.model.User;
import com.ss.project.repository.IUserRepository;
import com.ss.project.response.ErrorField;
import com.ss.project.response.Reponse;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserRepository userRepository;

	@Override
	public ResponseEntity<?> createUser(UserDTO userDto) {
		ResponseEntity<?> re = null;
		Reponse response = null;
		User user = null;
		try {
			response = validateRequest(userDto, AppConstant.CREATE);
			if (response.getValErrors() != null && response.getValErrors().size() > 0) {
				response.setResMsg("Validation has failed.");
				re = new ResponseEntity<Reponse>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			} else {
				user = userRepository.findByEMailAndIsActive(userDto.geteMail(), Boolean.TRUE);
				if (user == null) {
					user = userDto.convertToUser(userDto);
					user.setActive(Boolean.TRUE);
					user.setId(null);
					user = userRepository.save(user);
					response.setResMsg("User Created Successfully");
					response.setUserId(String.valueOf(user.getId()));
					re = new ResponseEntity<Reponse>(response, HttpStatus.OK);
				} else {
					response.setResMsg("User with same E-Mail Already exist.");
					re = new ResponseEntity<Reponse>(response, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			}
		} catch (Exception e) {
			response.setResMsg("Exception: " + e.getMessage());
			re = new ResponseEntity<Reponse>(response, HttpStatus.BAD_REQUEST);
		}
		return re;
	}

	@Override
	public ResponseEntity<?> updateUser(UserDTO userDto) {
		ResponseEntity<?> re = null;
		Reponse response = null;
		User user = null;
		try {
			response = validateRequest(userDto, AppConstant.UPDATE);
			if (response.getValErrors() != null && response.getValErrors().size() > 0) {
				response.setResMsg("Validation has failed.");
				response.setUserId(userDto.getId() == null ? null : String.valueOf(userDto.getId()));
				re = new ResponseEntity<Reponse>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			} else {
				user = userRepository.findOne(userDto.getId());
				if (user == null) {
					response.setResMsg("User ID not Found");
					response.setUserId(userDto.getId() == null ? null : String.valueOf(userDto.getId()));
					re = new ResponseEntity<Reponse>(response, HttpStatus.UNPROCESSABLE_ENTITY);
				} else {
					if (userDto.getPinCode() != null) {
						user.setPinCode(userDto.getPinCode());
					}
					if (userDto.getBirthDate() != null) {
						user.setBirthDate(userDto.getBirthDate());
					}
					response.setUserId(String.valueOf(userRepository.save(user)));
					response.setResMsg("User Updated Successfully");
					response.setUserId(String.valueOf(user.getId()));
					re = new ResponseEntity<Reponse>(response, HttpStatus.OK);
				}
			}
		} catch (Exception e) {
			response.setResMsg("Exception: " + e.getMessage());
			re = new ResponseEntity<Reponse>(response, HttpStatus.BAD_REQUEST);
		}
		return re;
	}

	@Override
	public ResponseEntity<?> deleteUser(UserDTO userDto) {
		ResponseEntity<?> re = null;
		Reponse response = null;
		User user = null;
		try {
			response = validateRequest(userDto, AppConstant.DELETE);
			if (response.getValErrors() != null && response.getValErrors().size() > 0) {
				response.setResMsg("Validation has failed.");
				response.setUserId(userDto.getId() == null ? null : String.valueOf(userDto.getId()));
				re = new ResponseEntity<Reponse>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			} else {
				user = userRepository.findOne(userDto.getId());
				if (user == null) {
					response.setResMsg("User ID not Found");
					response.setUserId(userDto.getId() == null ? null : String.valueOf(userDto.getId()));
					re = new ResponseEntity<Reponse>(response, HttpStatus.UNPROCESSABLE_ENTITY);
				} else {
					user.setActive(false);
					response.setUserId(String.valueOf(userRepository.save(user)));
					response.setResMsg("User Deleted Successfully");
					response.setUserId(String.valueOf(user.getId()));
					re = new ResponseEntity<Reponse>(response, HttpStatus.OK);
				}
			}
		} catch (Exception e) {
			response.setResMsg("Exception: " + e.getMessage());
			re = new ResponseEntity<Reponse>(response, HttpStatus.BAD_REQUEST);
		}
		return re;
	}

	private Reponse validateRequest(UserDTO userDto, String type) {
		List<ErrorField> list = null;
		if (AppConstant.CREATE.equals(type)) {
			list = new ArrayList<>();
			if (userDto.getfName() == null) {
				list.add(new ErrorField("First Name cannot be null", "fName", "Fist Name is mandatory"));
			}
			if (userDto.getlName() == null) {
				list.add(new ErrorField("Last Name cannot be null", "lName", "Last Name is mandatory"));
			}
			if (userDto.geteMail() == null) {
				list.add(new ErrorField("E-Mail cannot be null", "eName", "E-Mail is mandatory"));
			} else if (userDto.geteMail().trim() == "" || !userDto.geteMail().contains("@")) {
				list.add(new ErrorField("E-Mail format is incorrect", "eName", "E-Mail address is not valid"));
			}

			if (userDto.getPinCode() == null) {
				list.add(new ErrorField("PIN Code cannot be null", "pinCode", "PIN Code is mandatory"));
			} else if (userDto.getPinCode().intValue() <= 0) {
				list.add(new ErrorField("PIN Code cannot be 0 or Negetive", "pinCode", "PIN Code is invalid"));
			}
			if (userDto.getBirthDate() == null) {
				list.add(new ErrorField("Date of Birth cannot be null", "birthDate", "Date of Birth is mandatory"));
			} else if ((new Date()).compareTo(userDto.getBirthDate()) <= 0) {
				list.add(new ErrorField("Date of Birth cannot be Future Date", "birthDate",
						"Date of Birth cannot be Future Date"));
			}

		} else if (AppConstant.UPDATE.equals(type)) {
			list = new ArrayList<>();
			if (userDto.getId() == null) {
				list.add(new ErrorField("User ID cannot be null", "Id", "User ID is mandatory"));
			}
			if (userDto.getPinCode() != null && userDto.getPinCode().intValue() <= 0) {
				list.add(new ErrorField("PIN Code cannot be 0 or Negetive", "pinCode", "PIN Code is invalid"));
			}
			if (userDto.getBirthDate() != null && (new Date()).compareTo(userDto.getBirthDate()) <= 0) {
				list.add(new ErrorField("Date of Birth cannot be Future Date", "birthDate",
						"Date of Birth cannot be Future Date"));
			}
		} else if (AppConstant.DELETE.equals(type)) {
			list = new ArrayList<>();
			if (userDto.getId() == null) {
				list.add(new ErrorField("User ID cannot be null", "Id", "User ID is mandatory"));
			}

		}
		return new Reponse(null, null, list);
	}
}
