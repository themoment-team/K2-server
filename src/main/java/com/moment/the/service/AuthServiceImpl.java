package com.moment.the.service;

import com.moment.the.domain.AdminDomain;
import com.moment.the.domain.SaltDomain;
import com.moment.the.repository.AdminRepository;
import com.moment.the.util.SaltUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private SaltUtil saltUtil;
    @Override
    public void signUpUser(AdminDomain adminDomain) {
        String password = adminDomain.getAdminPwd();
        String salt = saltUtil.genSalt();
        adminDomain.setSalt(new SaltDomain(salt));
        adminDomain.setAdminPwd(saltUtil.encodePassword(salt, password));
        adminRepository.save(adminDomain);
    }
    @Override
    public AdminDomain loginUser(String id, String password) throws Exception {
        AdminDomain adminDomain = adminRepository.findByAdminId(id);
        if(adminDomain == null) throw new Exception("해당 관리자가 조회되지 않음.");
        String salt = adminDomain.getSalt().getSalt();
        password = saltUtil.encodePassword(salt, password);
        if(!adminDomain.getAdminPwd().equals(password))
            throw new Exception("비밀번호가 틀립니다.");
        return adminDomain;
    }
}
