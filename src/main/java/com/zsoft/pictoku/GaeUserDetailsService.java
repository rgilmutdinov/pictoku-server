package com.zsoft.pictoku;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zsoft.pictoku.model.GaeUser;
import com.zsoft.pictoku.persistence.repository.GaeUserRepository;

@Service
public class GaeUserDetailsService implements UserDetailsService {

	@Autowired
	private GaeUserRepository users;

	@Override
	public GaeUser loadUserByUsername(String email) throws UsernameNotFoundException {
		GaeUser user = users.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("No user found with email: " + email);
		}
		return user;
	}
}
