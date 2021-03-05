package com.moment.the.repository;

import com.moment.the.domain.AuthDomain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<AuthDomain, Long> {
    Optional<AuthDomain> findByAuthEmail(String authEmail);
}
