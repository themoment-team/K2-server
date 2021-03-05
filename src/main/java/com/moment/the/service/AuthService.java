package com.moment.the.service;

import com.moment.the.domain.AuthDomain;
import com.moment.the.dto.AuthDto;
import com.moment.the.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    @Autowired
    private AuthRepository authRepository;

    @Transactional
    public AuthDomain approveEmail(AuthDto authDto){
        if(authRepository.findByAuthEmail(authDto.getAuthEmail()).orElse(null) != null){
            throw new RuntimeException("이미 가입된 유저입니다.");
        }
        AuthDomain authDomain = AuthDomain.builder()
                .authEmail(authDto.getAuthEmail())
                .build();

        return authRepository.save(authDomain);
    }

    @Transactional
    public void updateEmail(String email){
        AuthDomain authDomain = authRepository.findByAuthEmail(email).orElseThrow();
        authDomain.setAuthEmail(email);
    }
}
