package com.liberty.doshka.dao;

import com.liberty.doshka.form.UserForm;
import com.liberty.doshka.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserDAO {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Map<Long, User> USERS_MAP = new HashMap<>();

    public User findAppUserByUserName(String userName) {
        Collection<User> appUsers = USERS_MAP.values();
        for (User u : appUsers) {
            if (u.getUserName().equals(userName)) {
                return u;
            }
        }
        return null;
    }

    public Long getMaxUserId() {
        long max = 0;
        for (Long id : USERS_MAP.keySet()) {
            if (id > max) {
                max = id;
            }
        }
        return max;
    }

    public User findUserByEmail(String email){
        Collection<User> users = USERS_MAP.values();
        for (User u : users){
            if(u.getEmail().equals(email)){
                return u;
            }
        }
        return null;
    }

    public List<User> getAllUsers(){
        List<User> allUsers = new ArrayList<>();
        for (User val : USERS_MAP.values()) {
            allUsers.add(val);
        }
        return allUsers;
    }

    public User createUser(UserForm userForm){
        Long userId = this.getMaxUserId() + 1;
        String encrytedPassword = this.passwordEncoder.encode(userForm.getPassword());

        User user = new User(userId, userForm.getUserName(), //
                userForm.getFirstName(), userForm.getLastName(),
                userForm.getEmail(), //
                encrytedPassword);

        USERS_MAP.put(userId, user);
        return user;
    }

}
