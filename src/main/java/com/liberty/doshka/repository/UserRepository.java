package com.liberty.doshka.repository;

import com.liberty.doshka.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {

    User findOneByEmailAddress(String emailAddress);

    User findUserByUserName(String userName);

}
