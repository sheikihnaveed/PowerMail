package com.alfaminds.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.alfaminds.models.UserModel;

@Repository
public interface UserRepository extends MongoRepository<UserModel, String> {
	UserModel findByUsername(String username);
	
	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	
	UserModel findByEmail(String email);
	
	UserModel findByVerificationCode(String code);

	
}


