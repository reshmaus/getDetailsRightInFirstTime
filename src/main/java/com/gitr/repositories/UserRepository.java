package com.gitr.repositories;

import com.gitr.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository// used to indicate that the class provides the mechanism for storage, retrieval, search,
// update and delete operation on objects
//JPA (Java persistant API) used for managing the data in spring boot application
public interface UserRepository extends JpaRepository<User, Long> {
   Optional<User> findByUserName(String userName);
}
