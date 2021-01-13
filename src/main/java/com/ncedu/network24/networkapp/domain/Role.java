package com.ncedu.network24.networkapp.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMIN, BLOCKED;

    @Override
    public String getAuthority() {
        return name();
    }
}
