package com.alfaminds.services;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.alfaminds.models.UserModel;

public class CustomServiceDetails implements UserDetails {

	private String Email;

	private UserModel us;

	public CustomServiceDetails(UserModel us) {
		this.us = us;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		return us.getPassword();
	}

	@Override
	public String getUsername() {
		return us.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	public String getFullName() {
		return us.getFullname();
	}

	@Override
	public boolean isEnabled() {
		return us.isEnabled();
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

}
