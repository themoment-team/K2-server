package com.moment.the.config.security;

import com.moment.the.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {
    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return adminRepository.findByAdminId(email);
    }
}
