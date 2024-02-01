package com.ct_ecommerce.eshop.AppUsers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
    @Query("SELECT user FROM AppUser user WHERE user.email = :email")
    Optional<AppUser> findUserByEmail(@Param("email") String email);
    @Query("SELECT user FROM AppUser user WHERE user.username = :username")
    Optional<AppUser> findUserByUsername(@Param("username") String username);
}
