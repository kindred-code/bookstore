package com.mpolitakis.bookstore.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpolitakis.bookstore.models.User;


public interface UserRepository extends JpaRepository<User, Integer> {

  User findByUsername(String username);

  Optional<User> findById(Long userId);

  

}
