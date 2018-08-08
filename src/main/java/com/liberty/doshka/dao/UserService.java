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
    private static UserRepository userRepository;

//    static {
//        initDATA();
//    }
//
//    private static void initDATA() {
//        String encrytedPassword = "";
//
//        User tom = new User(1L, "tom", "Tom", "Tom", "tom@waltdisney.com", encrytedPassword);
//
//        User jerry = new User(2L, "jerry", "Jerry", "Jerry", "jerry@waltdisney.com", encrytedPassword);
//
////        USERS_MAP.put(tom.getUserId(), tom);
////        USERS_MAP.put(jerry.getUserId(), jerry);
//        userRepository.save(tom);
//        userRepository.save(jerry);
//
//    }

//    private static final Map<Long, User> USERS_MAP = new HashMap<>();

//    public User findAppUserByUserName(String userName) {
//        Collection<User> appUsers = userRepository.findAll();
//        for (User u : appUsers) {
//            if (u.getUserName().equals(userName)) {
//                return u;
//            }
//        }
//        return null;
//    }

    public Long getMaxUserId() {
        long max = 0;
        for (User user : userRepository.findAll()) {
            Long id = user.getUserId();
            if (id > max) {
                max = id;
            }
        }
        return max;
    }

//    public User findUseByEmail(String emailAddress){
//        Collection<User> users = userRepository.findAll();
//        for (User u : users){
//            if(u.getEmailAddress().equals(emailAddress)){
//                return u;
//            }
//        }
//        return null;
//    }

//    public List<User> getAllUsers(){
//        List<User> allUsers = new ArrayList<>();
//        for (User val : userRepository.findAll()) {
//            allUsers.add(val);
//        }
//        return allUsers;
//    }

    public User createUser(UserForm userForm){
        Long userId = this.getMaxUserId() + 1;
        String encrytedPassword = this.passwordEncoder.encode(userForm.getPassword());

        User user = new User(userId, userForm.getUserName(), //
                userForm.getFirstName(), userForm.getLastName(),
                userForm.getEmailAddress(), //
                encrytedPassword);

//        USERS_MAP.put(userId, user);
        userRepository.save(user);
        return user;
    }

}
