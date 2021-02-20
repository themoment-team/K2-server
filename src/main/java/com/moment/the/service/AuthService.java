package com.moment.the.service;

import com.moment.the.domain.AdminDomain;

public interface AuthService {
    void signUpUser(AdminDomain adminDomain);
    AdminDomain loginUser(String id, String password);
}
