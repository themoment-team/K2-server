package com.moment.the.admin.service;

import com.moment.the.admin.AdminDomain;
import com.moment.the.admin.dto.AdminDto;
import com.moment.the.admin.dto.SignInDto;

public interface AdminService {
    void join(AdminDto adminDto) throws Exception;
    AdminDomain login(SignInDto signInDto) throws Exception;
    void logout();
    void withdrawal(SignInDto SignInDto) throws Exception;
}
