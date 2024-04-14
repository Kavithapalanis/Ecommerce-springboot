package com.jsonwebtoken.step1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jsonwebtoken.step1.entities.Role;
import com.jsonwebtoken.step1.entities.User;

@Repository
public interface UserRepository extends JpaRepository< User, Long> {// <classname:User , datatypeofourId:Long>
	
	Optional<User> findByEmail(String email);
	// first it should check is there a ADMIN account in database or not 
	//then only we can create an admin account other wise we will skip the process
	User findByRole(Role role);
	

}
