package com.example.Customer_Service.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.Customer_Service.service.JwtService;
import com.example.dto.credentialtoken;
import com.example.model.Customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {
	
	@Autowired
	private JwtService jwts;
	
	private String username;
	private String password;
	private List<GrantedAuthority> authorities;
	public CustomUserDetails(Customer cred) {
        this.username = cred.getEmail();
        this.password = cred.getPassword();
        this.authorities = Arrays.asList(cred.getRole().split(",")).stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
    }
	
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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

    @Override
    public boolean isEnabled() {
        return true;
    }
}
