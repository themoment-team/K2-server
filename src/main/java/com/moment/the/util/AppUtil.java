package com.moment.the.util;

import com.moment.the.admin.AdminDomain;
import com.moment.the.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppUtil {

    private final AdminRepository adminRepository;

    public static String getCurrentAdminEmail(){
        String userEmail;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            userEmail = ((UserDetails) principal).getUsername();
        } else {
            userEmail = principal.toString();
        }
        return userEmail;
    }

    public AdminDomain getCurrentAdminEntity(){
        return adminRepository.findByEmail(getCurrentAdminEmail());
    }
}
