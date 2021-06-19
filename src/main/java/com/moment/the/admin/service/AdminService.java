package com.moment.the.admin;

import com.moment.the.admin.AdminDto;
import com.moment.the.admin.SignInDto;

import java.util.Map;

public interface AdminService {
    void signUp(AdminDto adminDto) throws Exception;
    Map<String, String> loginUser(String id, String password) throws Exception;
    void logout();
    void withdrawal(SignInDto SignInDto) throws Exception;
}
