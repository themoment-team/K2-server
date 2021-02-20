package com.moment.the.service;

import com.moment.the.domain.AdminDomain;
import com.moment.the.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public void signUpUser(AdminDomain adminDomain) {
        String password = adminDomain.getAdminPwd();
        String salt = salt
    }

    @Override
    public AdminDomain loginUser(String id, String password) {
        return null;
    }
}
