package com.eazybytes.jensenstore.service.impl;

import com.eazybytes.jensenstore.dto.ProfileResponseDto;
import com.eazybytes.jensenstore.entity.Customer;
import com.eazybytes.jensenstore.repository.CustomerRepository;
import com.eazybytes.jensenstore.service.IProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements IProfileService {

    private final CustomerRepository customerRepository;


    @Override
    public ProfileResponseDto getProfile() {
        Customer customer = getAuthenticatedCustomer();
        return mapCustomerToProfileResponseDto(customer);
    }

    private Customer getAuthenticatedCustomer() {
        // 1. 從 SecurityContext 獲取 Authentication 物件
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 2. 取得 Email (在 JWTTokenValidatorFilter 中被設定為 username)
        String email = authentication.getName();

        // 3. 查詢資料庫，若無則拋出例外
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    private ProfileResponseDto mapCustomerToProfileResponseDto(Customer customer) {
        ProfileResponseDto profileResponseDto = new ProfileResponseDto();
        // 將 customer 的屬性複製到 dto (需欄位名稱相同)
        BeanUtils.copyProperties(customer, profileResponseDto);
        return profileResponseDto;
    }
}
