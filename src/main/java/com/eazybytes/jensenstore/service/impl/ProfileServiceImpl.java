package com.eazybytes.jensenstore.service.impl;

import com.eazybytes.jensenstore.dto.AddressDto;
import com.eazybytes.jensenstore.dto.ProfileRequestDto;
import com.eazybytes.jensenstore.dto.ProfileResponseDto;
import com.eazybytes.jensenstore.entity.Address;
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

    @Override
    public ProfileResponseDto updateProfile(ProfileRequestDto profileRequestDto) {

        Customer customer = getAuthenticatedCustomer();

        boolean emailUpdated=!customer.getEmail().equalsIgnoreCase(profileRequestDto.getEmail());


        BeanUtils.copyProperties(profileRequestDto,customer);


       Address address = customer.getAddress();

        if (address == null) {
            address = new Address();
            address.setCustomer(customer);
        }

        address.setCity(profileRequestDto.getCity());
        address.setCountry(profileRequestDto.getCountry());
        address.setState(profileRequestDto.getState());
        address.setPostalCode(profileRequestDto.getPostalCode());
        address.setStreet(profileRequestDto.getStreet());


        customer.setAddress(address);


        customerRepository.save(customer);

        ProfileResponseDto profileResponseDto = mapCustomerToProfileResponseDto(customer);
        profileResponseDto.setEmailUpdated(emailUpdated);

        return profileResponseDto;
    }

    public Customer getAuthenticatedCustomer() {
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

        if (customer.getAddress()!=null){
            AddressDto addressDto = new AddressDto();
            BeanUtils.copyProperties(customer.getAddress(),addressDto);
            profileResponseDto.setAddress(addressDto);
        }

        return profileResponseDto;
    }
}
