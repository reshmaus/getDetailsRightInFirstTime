package com.gitr.repositories;

import com.gitr.entities.Guest;
import com.gitr.entities.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    Optional<Provider> findByFirstName(String firstName);
}