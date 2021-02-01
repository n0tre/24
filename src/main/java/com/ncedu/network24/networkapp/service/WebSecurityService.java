package com.ncedu.network24.networkapp.service;
import org.springframework.security.crypto.password.PasswordEncoder;


    public final class WebSecurityService implements PasswordEncoder {
        private static final PasswordEncoder INSTANCE = new WebSecurityService();

        private WebSecurityService() {
        }

        public String encode(CharSequence rawPassword) {
            return rawPassword.toString();
        }

        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return rawPassword.toString().equals(encodedPassword);
        }

        public static PasswordEncoder getInstance() {
            return INSTANCE;
        }
    }

