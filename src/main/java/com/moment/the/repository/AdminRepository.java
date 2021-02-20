package com.moment.the.repository;

import com.moment.the.domain.AdminDomain;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<AdminDomain, Long> {
    AdminDomain findByAdminName(String adminName);
}
