package com.thapasya.infopark.repository;

import com.thapasya.infopark.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<com.thapasya.infopark.models.User, Long> {
    Optional<User> findByEmail(String email);
}
