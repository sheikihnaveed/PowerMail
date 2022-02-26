package com.alfaminds.services;


import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.alfaminds.Repository.UserRepository;
import com.alfaminds.models.UserModel;

@Service
public class UserServices implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserModel userfounded = userRepository.findByEmail(email);
		if (userfounded == null) 
			return null;
		
			
		
		
		String name = userfounded.getEmail();
		String pass = userfounded.getPassword();
		return new User(name, pass, new ArrayList<>());
	}	

}
