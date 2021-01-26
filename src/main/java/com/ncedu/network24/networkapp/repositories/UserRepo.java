package com.ncedu.network24.networkapp.repositories;

import com.ncedu.network24.networkapp.domain.Chat;
import com.ncedu.network24.networkapp.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String username);


    @Query("UPDATE User u SET u.failedAttempt = ?1 WHERE u.username = ?2")
    @Modifying
    public void updateFailedAttempts(int failAttempts, String username);





}
