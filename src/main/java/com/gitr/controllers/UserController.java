package com.gitr.controllers;

import com.gitr.dtos.UserDto;
import com.gitr.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//  need a controller method to show the initial HTML form,will handle all request
@RestController
// needs this to create a rest users Api path as base url to access
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // This rest Api for registering/ adding a new user
    @PostMapping("/register")
    public List<String> addUser(@RequestBody UserDto userDto){
        String passHash = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(passHash);
        return  userService.addUser((userDto));
    }

    // This rest Api for login user
    @PostMapping("/login")
    public List<String> userLogin(@RequestBody UserDto userDto){

        return userService.userLogin(userDto);
    }
}
