package com.liberty.doshka.dao;

import com.liberty.doshka.form.UserForm;
import com.liberty.doshka.model.User;
import com.liberty.doshka.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public Long getMaxUserId() {
        long max = 0;
        if (userRepository.count() >1){
            for (User user : userRepository.findAll()) {
                Long id = user.getUserId();
                if (id > max) {
                    max = id;
                }
            }
        }
        return max;
    }

    public User createUser(UserForm userForm){
        Long userId = this.getMaxUserId() + 1;
        String encrytedPassword = this.passwordEncoder.encode(userForm.getPassword());

        User user = new User(userId, userForm.getUserName(), //
                userForm.getFirstName(), userForm.getLastName(),
                userForm.getEmailAddress(), //
                encrytedPassword);

        userRepository.save(user);
        return user;
    }

}
