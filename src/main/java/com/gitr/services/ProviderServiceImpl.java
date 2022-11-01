package com.gitr.services;

import com.gitr.dtos.ProviderDto;
import com.gitr.entities.Provider;
import com.gitr.repositories.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProviderServiceImpl implements ProviderService  {
    @Autowired//  Spring is able to find the corresponding
    // dependency and inject it where it is needed throughout the application
    private ProviderRepository providerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    // This is where a new provider will be added to DB
    public List<String> addProvider(ProviderDto providerDto) {
        List<String> response = new ArrayList<>();
        Provider provider = new Provider(providerDto);
        providerRepository.saveAndFlush(provider);
        response.add("http://localhost:8080/login.html");
        return response;

    }
    @Override
    // This is where an existing provider will be validated with his credentials and appropriate response is returned
    public List<String> providerLogin(ProviderDto providerDto) {
        List<String> response = new ArrayList<>();
        Optional<Provider> providerOptional = providerRepository.findByProviderName(providerDto.getProviderName());
        if(providerOptional.isPresent()){
            if(passwordEncoder.matches(providerDto.getPassword(), providerOptional.get().getPassword())){
                response.add("http://localhost:8080/home.html");
                response.add(String.valueOf(providerOptional.get().getId()));
            }else{
                response.add("Provider password incorrect");
            }
        }else{
            response.add("Provider name is incorrect");
        }
        return response;
    }
}
