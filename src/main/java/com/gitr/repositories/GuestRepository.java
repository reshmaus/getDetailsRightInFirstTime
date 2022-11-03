package com.gitr.repositories;

import com.gitr.entities.Guest;
import com.gitr.entities.Provider;
import com.gitr.entities.User;
import com.gitr.entities.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    Optional<Provider> findByFirstName(String firstName);
    List<Guest> findAllByProviderEquals(Provider provider);
}