package com.openclassrooms.mddapi.repositories;

import java.util.*;

import com.openclassrooms.mddapi.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  Optional<User> findByUsername(String username);

  Boolean existsByEmail(String email); 

  Boolean existsByUsername(String username); 

}
