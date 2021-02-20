package com.example.restspringboot.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userCredentialsRepository extends JpaRepository<UserCredentials, Long> {

}
