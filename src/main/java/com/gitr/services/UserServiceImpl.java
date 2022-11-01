package com.gitr.services;

import com.gitr.dtos.UserDto;
import com.gitr.entities.User;
import com.gitr.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//This is where actual Bussiness logic is for User scope, the fetch and push to DB happens
@Service
public class UserServiceImpl implements UserService {

    @Autowired//  Spring is able to find the corresponding
    // dependency and inject it where it is needed throughout the application
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    // This is where a new user will be added to DB
    public List<String> addUser(UserDto userDto) {
        List<String> response = new ArrayList<>();
        User user = new User(userDto);
        userRepository.saveAndFlush(user);
        response.add("http://localhost:8080/login.html");
        return response;

    }
        @Override
        // This is where an existing user will be validated with his credentials and appropriate response is returned
        public List<String> userLogin(UserDto userDto) {
            List<String> response = new ArrayList<>();
            Optional<User> userOptional = userRepository.findByUserName(userDto.getUserName());
             if(userOptional.isPresent()){
                 if(passwordEncoder.matches(userDto.getPassword(), userOptional.get().getPassword())){
                     response.add("successful");
                     response.add(String.valueOf(userOptional.get().getId()));
                 }else{
                     response.add("error");
                     response.add("User password incorrect");
                 }

             }else{
                 response.add("error");
                 response.add("User name is incorrect");
             }


            return response;
        }



}
