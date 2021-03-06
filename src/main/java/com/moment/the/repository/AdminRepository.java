package com.moment.the.repository;

import com.moment.the.domain.AdminDomain;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdminRepository extends JpaRepository<AdminDomain, Long> {
    AdminDomain findByAdminId(String adminId);
}
