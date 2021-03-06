package com.moment.the.repository;

import com.moment.the.domain.AuthDomain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<AuthDomain, Long> {
     AuthDomain findByAuthEmail(String authEmail);
}
