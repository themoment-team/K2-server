package com.moment.the.admin.repository;

import com.moment.the.admin.AdminDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminDomain, Long> {
    AdminDomain findByEmail(String email);
    AdminDomain findByEmailAndPassword(String email, String password);
}
