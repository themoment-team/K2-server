package com.moment.the.service;

import com.moment.the.domain.AuthDomain;
import com.moment.the.dto.AuthDto;
import com.moment.the.dto.AuthEditDto;
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
        AuthDomain here = authRepository.findByAuthEmail(authDto.getAuthEmail());
        if(here != null){
            throw new RuntimeException("이미 가입된 유저입니다.");
        }
        AuthDomain authDomain = AuthDomain.builder()
                .authEmail(authDto.getAuthEmail())
                .build();

        return authRepository.save(authDomain);
    }

    @Transactional
    public void editEmail(AuthEditDto authEditDto){
        AuthDomain here = authRepository.findByAuthEmail(authEditDto.getExistingEmail());
        if(here == null){
            throw new RuntimeException("존재하지 않는 아이디 입니다.");
        }
        else {
            here.setAuthEmail(authEditDto.getEditEmail());
        }
    }
}
