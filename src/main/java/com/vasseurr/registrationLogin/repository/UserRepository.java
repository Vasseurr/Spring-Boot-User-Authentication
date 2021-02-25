package com.vasseurr.registrationLogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vasseurr.registrationLogin.model.User;

public interface UserRepository extends JpaRepository <User,Long>{

}
