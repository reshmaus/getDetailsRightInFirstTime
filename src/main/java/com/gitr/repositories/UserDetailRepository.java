package com.gitr.repositories;

import com.gitr.entities.User;
import com.gitr.entities.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
    List<UserDetail> findAllByUserEquals(User user);
}