package com.gitr.controllers;

import com.gitr.dtos.GuestDto;
import com.gitr.dtos.UserDetailDto;
import com.gitr.services.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//  need a controller method to show the initial HTML form,will handle all request
@RestController
// needs this to create a rest notes Api path as base url to access
@RequestMapping("api/v1/userDetail")
public class UserDetailController {
    @Autowired
    private UserDetailService userDetailService;

    // This is rest Api for getting user all user detail by user id
    @GetMapping("/getAllByUserId/{userId}")
    public List<UserDetailDto> getAllUserDetailByUserId(@PathVariable Long userId){
        return userDetailService.getAllUserDetailByUserId(userId);
    }

    // This is rest Api for adding/creating user detail under given user id
    @PostMapping("/create/{userId}")
    public Optional<UserDetailDto> addUserUserDetail(@RequestBody UserDetailDto userDetailDto, @PathVariable Long userId){
        return userDetailService.addUserUserDetail(userDetailDto, userId);
    }

    // This is rest Api for delete the specific user detail by user detail id
    @DeleteMapping("/delete/{userDetailId}")
    public void deleteUserDetailById(@PathVariable Long userDetailId){
        userDetailService.deleteUserDetailById(userDetailId);
    }

    @GetMapping("/getById/{userDetailId}")
    public Optional<UserDetailDto> getUserDetailById(@PathVariable Long userDetailId){
        return userDetailService.getUserDetailById(userDetailId);
    }

    // This is rest Api for updating the user detail
    @PutMapping("/update")
    public Optional<UserDetailDto> updateUserDetailById(@RequestBody UserDetailDto userDetailDto){
        return userDetailService.updateUserDetailById(userDetailDto);
    }
}
