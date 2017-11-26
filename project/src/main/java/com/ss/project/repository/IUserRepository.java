package com.ss.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ss.project.model.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

	User findByEMailAndIsActive(String eMail, Boolean isActive);

}
