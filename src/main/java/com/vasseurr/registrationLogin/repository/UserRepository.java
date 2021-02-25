package com.vasseurr.registrationLogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vasseurr.registrationLogin.model.User;

@Repository
public interface UserRepository extends JpaRepository <User,Long>{
	
	//Custom Query
	//@Query("SELECT u FROM User u WHERE u.email = ?1")
	User findByEmail(String email);
}
