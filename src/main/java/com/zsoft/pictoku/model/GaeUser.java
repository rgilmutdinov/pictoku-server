package com.zsoft.pictoku.model;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@PersistenceCapable
public class GaeUser implements UserDetails {

	private static final long serialVersionUID = 6913751764171534317L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String name;

	@JsonProperty("email")
	@Persistent
	private String email;

	@JsonIgnore
	@Persistent
	private String password;

	@NotPersistent
	private List<GaeGrantedAuthority> grantedAuthorities = new ArrayList<GaeGrantedAuthority>();

	public GaeUser() {
		GaeGrantedAuthority grantedAuthority = new GaeGrantedAuthority();
		grantedAuthority.setUserRole(UserRole.ROLE_USER);
		grantedAuthorities.add(grantedAuthority);
	}

	public GaeUser(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setAuthorities(List<GaeGrantedAuthority> authorities) {
		grantedAuthorities = authorities;
	}

	@JsonIgnore
	@Override
	public List<GaeGrantedAuthority> getAuthorities() {
		return grantedAuthorities;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setUsername(String email) {
		this.email = email;
	}

	@JsonIgnore
	@Override
	public String getUsername() {
		return email;
	}

	public String getEmail() {
		return email;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}
}
