package com.ncedu.network24.networkapp.service;

import com.ncedu.network24.networkapp.component.SessionUtils;
import com.ncedu.network24.networkapp.domain.User;
import com.ncedu.network24.networkapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class UserService implements UserDetailsService {


    @Value("${LOCK_TIME_DURATION}")
    private long LOCK_TIME_DURATION;

    @Autowired
    private UserRepo userRepo;


    @Autowired
    private SessionUtils sessionUtils;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }


    public void increaseFailedAttempts(User user) {
        int newFailAttempts = user.getFailedAttempt() + 1;
        userRepo.updateFailedAttempts(newFailAttempts, user.getUsername());
    }

    public void resetFailedAttempts(String username) {
        userRepo.updateFailedAttempts(0, username);
    }

    public void lock(User user) {
        user.setAccountNonLocked(false);
        user.setLockTime(new Date());
        userRepo.save(user);
    }

    public boolean unlockWhenTimeExpired(User user) {
        long lockTimeInMillis = user.getLockTime().getTime();
        long currentTimeInMillis = System.currentTimeMillis();

        if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
            user.setAccountNonLocked(true);
            user.setLockTime(null);
            user.setFailedAttempt(0);

            userRepo.save(user);

            return true;
        }

        return false;
    }

    public void lockUser(User user) {
        user.setAccountNonLocked(false);
        userRepo.save(user);
        sessionUtils.expireUserSessions(user.getUsername());
        sessionUtils.killExpiredSessionForSure(user.getUsername());
    }

    public void unlockUser(User user) {
        user.setAccountNonLocked(true);
        user.setLockTime(null);
        user.setFailedAttempt(0);
        userRepo.save(user);

    }
}
