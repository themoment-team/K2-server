package com.moment.the.service;

import com.moment.the.domain.AdminDomain;
import com.moment.the.dto.AdminDto;

public interface AuthService {
    void signUp(AdminDto adminDto);
    AdminDomain loginUser(String id, String password) throws Exception;
}
