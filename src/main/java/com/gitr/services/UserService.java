package com.gitr.services;

import com.gitr.dtos.UserDto;

import javax.transaction.Transactional;
import java.util.List;

//This is the interface for accessing the methods
public interface UserService {
    @Transactional//which ensures that the transaction that gets opened with your datasource gets resolved
    List<String> addUser(UserDto userDto);

    List<String> userLogin(UserDto userDto);
}
