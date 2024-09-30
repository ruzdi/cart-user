package com.artomus.cartuser.model;

import com.artomus.cartuser.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class CartUserDetails implements UserDetails {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Boolean active;
    protected Collection<? extends GrantedAuthority> authorities;

    public CartUserDetails() {

    }

    public CartUserDetails(User user) {
        this.username = user.getEmail();
        this.password = user.getPassword();
        this.active = user.getActive();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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
        return active;
    }




}
