package com.zsoft.pictoku.model;

import org.springframework.security.core.GrantedAuthority;

public class GaeGrantedAuthority implements GrantedAuthority {
	private static final long serialVersionUID = 7008688472536415687L;

	private UserRole role = UserRole.ROLE_USER;

	public GaeGrantedAuthority() {
	}

	public void setUserRole(UserRole role) {
		this.role = role;
	}

	public UserRole getRole() {
		return role;
	}

	@Override
	public String getAuthority() {
		return role.name();
	}
}
