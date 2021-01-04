package com.ncedu.network24.networkapp.repositories;

import com.ncedu.network24.networkapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);

}
