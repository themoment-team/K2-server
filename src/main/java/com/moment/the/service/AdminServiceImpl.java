package com.moment.the.service;

import com.moment.the.advice.exception.UserAlreadyExistsException;
import com.moment.the.advice.exception.UserNotFoundException;
import com.moment.the.domain.AdminDomain;
import com.moment.the.domain.AnswerDomain;
import com.moment.the.dto.AdminDto;
import com.moment.the.dto.SignInDto;
import com.moment.the.repository.AdminRepository;
import com.moment.the.util.RedisUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AnswerService answerService;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisUtil redisUtil;

    @Override
    public void signUp(AdminDto adminDto) {
        if(adminRepository.findByAdminId(adminDto.getAdminId()) != null){
            throw new UserAlreadyExistsException();
        }
        adminDto.setAdminPwd(passwordEncoder.encode(adminDto.getAdminPwd()));
        adminRepository.save(adminDto.toEntity());
    }

    @Override
    public AdminDomain loginUser(String id, String password) {
        // 아이디 검증
        AdminDomain adminDomain = adminRepository.findByAdminId(id);
        if (adminDomain == null) throw new UserNotFoundException();
        // 비밀번호 검증
        boolean passwordCheck = passwordEncoder.matches(password, adminDomain.getPassword());
        if (!passwordCheck) throw new UserNotFoundException();
        return adminDomain;
    }

    // 로그아웃
    @Override
    public void logout() {
        String userEmail = this.getUserEmail();
        redisUtil.deleteData(userEmail);
    }

    @Override
    public void withdrawal(SignInDto signInDto) throws Exception {
        System.out.println("=======is come======");
        if (getUserEmail() == signInDto.getAdminId()) {
            System.out.println("========"+getUserEmail()+"========");
            AdminDomain adminDomain = loginUser(signInDto.getAdminId(), signInDto.getAdminPwd());
            System.out.println("=======is ready=========");
            adminRepository.delete(adminDomain);
            System.out.println("=======is deleted========");
        } else {
            throw new Exception("로그인 후 이용해주세요.");
        }
    }

    //현재 사용자의 ID를 Return
    public String getUserEmail() {
        String userEmail;
        AdminDomain principal = (AdminDomain) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            userEmail = ((UserDetails) principal).getUsername();
        } else {
            userEmail = principal.toString();
        }
        return userEmail;
    }
}
