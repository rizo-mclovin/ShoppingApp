package ru.connor.FirstSecurityApp.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.connor.FirstSecurityApp.model.Administration;

import java.util.Collection;
import java.util.Collections;


public class PersonDetails implements UserDetails {
    private final Administration administration;

    public PersonDetails(Administration administration) {
        this.administration = administration;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.singletonList(new SimpleGrantedAuthority(administration.getRole()));
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return this.administration.getPassword();
    }

    @Override
    public String getUsername() {
        return this.administration.getUsername();
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

    // Нужно, чтобы получать данные аутентифицированного пользователя
    public Administration getPerson() {
        return this.administration;
    }
}