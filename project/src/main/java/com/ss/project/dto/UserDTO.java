package com.ss.project.dto;

import java.util.Date;

import com.ss.project.model.User;

public class UserDTO {

	private Long id;

	private String fName;

	private String lName;

	private String eMail;

	private Integer pinCode;

	private Date birthDate;
	
	private boolean isActive;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public Integer getPinCode() {
		return pinCode;
	}

	public void setPinCode(Integer pinCode) {
		this.pinCode = pinCode;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public UserDTO convertToDto(User user) {
		UserDTO dto = new UserDTO();
		dto.setId(user.getId());
		dto.setfName(user.getfName());
		dto.seteMail(user.geteMail());
		dto.setlName(user.getlName());
		dto.setPinCode(user.getPinCode());
		dto.setBirthDate(user.getBirthDate());
		dto.setActive(user.isActive());
		return dto;

	}

	public User convertToUser(UserDTO user) {
		User u = new User();
		u.setId(user.getId());
		u.setfName(user.getfName());
		u.setlName(user.getlName());
		u.seteMail(user.geteMail());
		u.setPinCode(user.getPinCode());
		u.setBirthDate(user.getBirthDate());
		u.setActive(user.isActive());
		return u;

	}

}
