package com.tusofia.internshipprogram.repository;

import com.tusofia.internshipprogram.entity.user.User;
import com.tusofia.internshipprogram.enumeration.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

   Optional<User> findByUsername(String username);

   Optional<User> findByEmail(String email);

   List<User> findByRole(UserRole role);

   List<User> findByUserAllowed(Boolean userAllowed);
}
