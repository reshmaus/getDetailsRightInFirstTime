package com.gitr.services;

import com.gitr.dtos.GuestDto;
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
    public Optional<UserDetailDto> addUserUserDetail(UserDetailDto userDetailDto, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        UserDetail userDetail = new UserDetail(userDetailDto);
        userOptional.ifPresent(userDetail::setUser); 
        userDetailRepository.save(userDetail);
        return Optional.of(new UserDetailDto(userDetail));
    }

    @Override
    public void deleteUserDetailById(Long userDetailId) {
        Optional<UserDetail> userDetailOptional = userDetailRepository.findById(userDetailId);
        userDetailOptional.ifPresent(user -> userDetailRepository.delete(user));
    }

    @Override
    public Optional<UserDetailDto> updateUserDetailById(UserDetailDto userDetailDto) {
        Optional<UserDetail> userDetailOptional = userDetailRepository.findById(userDetailDto.getId() );
        userDetailOptional.ifPresent(userDetail -> {
            UserDetail userDetailUpdate = new UserDetail(userDetailDto);
            userDetail.setFirstName(userDetailDto.getFirstName());
            userDetail.setLastName(userDetailDto.getLastName());
            userDetail.setStreet1(userDetailDto.getStreet1());
            userDetail.setStreet2(userDetailDto.getStreet2());
            userDetail.setZipCode(userDetailDto.getZipCode());
            userDetail.setCity(userDetailDto.getCity());
            userDetail.setState(userDetailDto.getState());
            userDetail.setCountry(userDetailDto.getCountry());
            userDetail.setInsurance(userDetailDto.getInsurance());
            userDetail.setType(userDetailDto.getType());
            userDetail.setAdditionalDetails(userDetailDto.getAdditionalDetails());
            userDetail.setCreatedDate(userDetailDto.getCreatedDate());
            userDetail.setCreatedTime(userDetailDto.getCreatedTime());

            userDetailRepository.saveAndFlush(userDetail);
        });

        return Optional.of(new UserDetailDto(userDetailOptional.get()));
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
