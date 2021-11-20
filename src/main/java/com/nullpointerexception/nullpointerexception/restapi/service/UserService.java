package com.nullpointerexception.nullpointerexception.restapi.service;

import com.nullpointerexception.nullpointerexception.restapi.model.User;
import com.nullpointerexception.nullpointerexception.restapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {


    private final UserRepository userRepository;


    public User addNewUser(User user) {
       return userRepository.save(user);
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


}
