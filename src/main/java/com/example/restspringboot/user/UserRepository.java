package com.example.restspringboot.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  Optional<User> findByUsername(String username);

  @Transactional
  @Modifying
  @Query("UPDATE User u SET u.enabled = true WHERE u.email = ?1")
  int enableUser(String email);
}
