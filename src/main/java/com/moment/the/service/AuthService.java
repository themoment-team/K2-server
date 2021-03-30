package com.moment.the.service;

import com.moment.the.domain.AdminDomain;
import com.moment.the.dto.AdminDto;

public interface AuthService {
    void signUp(AdminDto adminDto) throws Exception;
    AdminDomain loginUser(String id, String password) throws Exception;
    void logout();
}
