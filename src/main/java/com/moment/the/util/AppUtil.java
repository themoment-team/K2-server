package com.moment.the.util;

import com.moment.the.admin.AdminDomain;
import com.moment.the.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * 애플리케이션에서 빈번히 발생하는 작업에 대해 Bean으로 등록하여 사용할 수 있습니다.
 *
 * @since 1.3.1
 * @author 전지환
 */
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
