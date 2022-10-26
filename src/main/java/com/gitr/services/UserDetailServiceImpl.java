package com.gitr.services;

import com.gitr.dtos.UserDetailDto;
import com.gitr.entities.User;
import com.gitr.entities.UserDetail;
import com.gitr.repositories.UserDetailRepository;
import com.gitr.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


//This is where actual Bussiness logic is for Note scope, the fetch and push to DB happens

// These services are used to execute CRUD SQL call using transactional and methods have been Implemented here
@Service
public class UserDetailServiceImpl implements UserDetailService{
    @Autowired
    private UserDetailRepository userDetailRepository;

//    @Autowired
//    private ProviderRepository providerRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addUserUserDetail(UserDetailDto userDetailDto, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        UserDetail userDetail = new UserDetail(userDetailDto);
        userOptional.ifPresent(userDetail::setUser);
        userDetailRepository.saveAndFlush(userDetail);
    }

    @Override
    public void deleteUserDetailById(Long userDetailId) {
        Optional<UserDetail> userDetailOptional = userDetailRepository.findById(userDetailId);
        userDetailOptional.ifPresent(user -> userDetailRepository.delete(user));
    }

    @Override
    public void updateUserDetailById(UserDetailDto userDetailDto) {
        Optional<UserDetail> userDetailOptional = userDetailRepository.findById(userDetailDto.getId() );
        userDetailOptional.ifPresent(userDetail -> {
            UserDetail userDetailUpdate = new UserDetail(userDetailDto);
            userDetailRepository.saveAndFlush(userDetailUpdate);
        });
    }

    @Override
    public List<UserDetailDto> getAllUserDetailByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            List<UserDetail> userDetailList = userDetailRepository.findAllByUserEquals(userOptional.get());
            return userDetailList.stream().map(userDetail -> new UserDetailDto(userDetail)).collect(Collectors.toList());

        }
        return Collections.emptyList();
    }

    @Override
    public Optional<UserDetailDto> getUserDetailById(Long UserDetailId) {
        Optional<UserDetail> userDetailOptional = userDetailRepository.findById(UserDetailId);
        if(userDetailOptional.isPresent()){
            return Optional.of(new UserDetailDto(userDetailOptional.get()));
        }
        return Optional.empty();
    }
}
