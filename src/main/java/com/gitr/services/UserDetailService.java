package com.gitr.services;

import com.gitr.dtos.UserDetailDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UserDetailService {
    @Transactional
    void addUserUserDetail(UserDetailDto userDetailDto, Long userId);

    @Transactional
    void deleteUserDetailById(Long userDetailId);

    @Transactional
    void updateUserDetailById(UserDetailDto userDetailDto);

    List<UserDetailDto> getAllUserDetailByUserId(Long userId);

    Optional<UserDetailDto> getUserDetailById(Long noteUserDetailId);
}
