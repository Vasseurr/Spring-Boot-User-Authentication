package com.vasseurr.registrationLogin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vasseurr.registrationLogin.model.User;
import com.vasseurr.registrationLogin.repository.UserRepository;
import com.vasseurr.registrationLogin.security.CustomUserDetails;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public UserService() {}

	public Object findByEmail(String email) {
		
		for(User user: userRepository.findAll()) {
			if(user.getEmail().equals(email))
				return user;
		}
		return "There is not an user who has " + email + "\n";
		
	}

	public void save(User user) {
		userRepository.save(user);
	}
	
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	public Optional<User> findById(long id) {
		return userRepository.findById(id);
	}

	//you can replace email with username
	//My project has not username field
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if(user == null)
			throw new UsernameNotFoundException("User not found");
			
		return new CustomUserDetails(user);
	}
}
