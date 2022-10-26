package com.gitr.repositories;

import com.gitr.entities.Guest;
import com.gitr.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest, Long> {
    List<Guest> findAllByUserEquals(Guest guest);
    //List<Guest> findAllByUserDetailEquals(UserDetail userDetail);
}