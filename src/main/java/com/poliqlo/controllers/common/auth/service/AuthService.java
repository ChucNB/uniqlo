package com.poliqlo.controllers.common.auth.service;

import com.poliqlo.models.TaiKhoan;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final RedisTemplate<String, String> redisTemplate;


    public Optional<String> getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return ((TaiKhoan) authentication.getPrincipal()).getEmail().describeConstable();
        }
        return Optional.empty();
    }
    public Optional<Integer> getAccountId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return ((TaiKhoan) authentication.getPrincipal()).getId().describeConstable();
        }
        return Optional.empty();
    }
    public Optional<String> getCurrentRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return ((TaiKhoan) authentication.getPrincipal()).getRole().describeConstable();
        }
        return Optional.empty();
    }

    public Optional<TaiKhoan> getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            try {
                var taiKhoan=(TaiKhoan)authentication.getPrincipal();
                return Optional.of(taiKhoan);
            } catch (ClassCastException e) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
    public Optional<Integer> getCurrentKhachHangId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof TaiKhoan) {
            TaiKhoan taiKhoan = (TaiKhoan) authentication.getPrincipal();
            if (taiKhoan.getKhachHang() != null) {
                return Optional.of(taiKhoan.getKhachHang().getId());
            }
        }
        return Optional.empty();
    }

    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal());
    }

}
