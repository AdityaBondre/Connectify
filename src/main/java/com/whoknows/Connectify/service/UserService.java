package com.whoknows.Connectify.service;

import com.whoknows.Connectify.entity.User;
import com.whoknows.Connectify.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public void saveUser(User user){
        userRepository.save(user);
    }
    public List<User> getAll() {
            return userRepository.findAll();
    }

    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }
    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
