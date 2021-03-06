package com.moment.the.repository;

import com.moment.the.domain.AdminDomain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminDomain, Long> {
    Optional<AdminDomain> findByAdminId(String adminId);
}
