package com.paymybuddy.paymybuddyserver.data.repository;

import com.paymybuddy.paymybuddyserver.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Find user by email.
     * @param email string email
     * @return userEntity
     */
    Optional<UserEntity> findUserEntityByEmail(String email);

    /**
     * Find user by email and password.
     * @param email string email
     * @param password string password
     * @return userEntity
     */
    Optional<UserEntity> findByEmailAndPassword(String email, String password);

    /**
     * Ask if user exist by email.
     * @param email string email
     * @return existing user (true) or not (false)
     */
    boolean existsByEmail(String email);
}
