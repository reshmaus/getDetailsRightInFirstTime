package com.gitr.controllers;

import com.gitr.dtos.ProviderDto;
import com.gitr.services.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//  need a controller method to show the initial HTML form,will handle all request
@RestController
// needs this to create a rest providers Api path as base url to access
@RequestMapping("/api/v1/providers")
public class ProviderController {
    @Autowired
    private ProviderService providerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // This rest Api for registering/ adding a new provider
    @PostMapping("/register")
    public List<String> addProvider(@RequestBody ProviderDto providerDto){
        String passHash = passwordEncoder.encode(providerDto.getPassword());
        providerDto.setPassword(passHash);
        return  providerService.addProvider((providerDto));
    }

    // This rest Api for login provider
    @PostMapping("/login")
    public List<String> providerLogin(@RequestBody ProviderDto providerDto){

        return providerService.providerLogin(providerDto);
    }
}
