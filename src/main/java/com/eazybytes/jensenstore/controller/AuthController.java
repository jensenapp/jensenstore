package com.eazybytes.jensenstore.controller;

import com.eazybytes.jensenstore.dto.*;
import com.eazybytes.jensenstore.entity.BaseEntity;
import com.eazybytes.jensenstore.entity.Customer;
import com.eazybytes.jensenstore.entity.Role;
import com.eazybytes.jensenstore.repository.CustomerRepository;
import com.eazybytes.jensenstore.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.authentication.password.CompromisedPasswordDecision;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    private final CompromisedPasswordChecker compromisedPasswordChecker;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequestDto registerRequestDto) {

        CompromisedPasswordDecision check = compromisedPasswordChecker.check(registerRequestDto.getPassword());

        if (check.isCompromised()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("password","Choose a strong password"));
        }

        Optional<Customer> existCustomer = customerRepository.findByEmailOrMobileNumber(registerRequestDto.getEmail(), registerRequestDto.getMobileNumber());

        Map<String,String> errors=new HashMap<>();

        if (existCustomer.isPresent()) {
            Customer customer = existCustomer.get();
            if (customer.getEmail().equalsIgnoreCase(registerRequestDto.getEmail())){
                errors.put("email","Email is already registered");
            }
            if (customer.getMobileNumber().equalsIgnoreCase(registerRequestDto.getMobileNumber())){
                errors.put("mobileNumber","Mobile number is already registered");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }


        Customer customer = new Customer();
        BeanUtils.copyProperties(registerRequestDto,customer);
        customer.setPasswordHash(passwordEncoder.encode(registerRequestDto.getPassword()));

        Role role = new Role();
        role.setName("ROLE_USER");
        customer.setRoles(Set.of(role));
        customerRepository.save(customer);

       return new ResponseEntity<>("Registration successful",HttpStatus.CREATED);
    }



    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> apiLogin(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            // 1. 執行驗證邏輯
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.username(),
                            loginRequestDto.password()
                    )
            );

            var loggedInUser = (Customer) authentication.getPrincipal(); // 取得登入者詳細資訊

            UserDto userDto = new UserDto();



            if (loggedInUser.getAddress()!=null) {
                AddressDto addressDto = new AddressDto();
                BeanUtils.copyProperties(loggedInUser.getAddress(),addressDto);
                userDto.setAddress(addressDto);
            }

            BeanUtils.copyProperties(loggedInUser,userDto);

            // 從 authentication 物件中取出 authorities 並轉為逗號分隔字串 (例如: "ROLE_USER,ROLE_ADMIN")
           userDto.setRoles(authentication.getAuthorities()
                   .stream()
                   .map(GrantedAuthority::getAuthority)
                   .collect(Collectors.joining(",")));


            String jwtToken = jwtUtil.generateJwtToken(authentication);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new LoginResponseDto(HttpStatus.OK.getReasonPhrase(),
                            userDto, jwtToken));


        } catch (BadCredentialsException ex) {
            // 3. 捕捉帳密錯誤 -> 回傳 401
            return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid username or password");

        } catch (AuthenticationException ex) {
            // 4. 捕捉其他驗證錯誤 -> 回傳 401
            return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Authentication failed");

        } catch (Exception ex) {
            // 5. 捕捉未知錯誤 -> 回傳 500
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }

    private ResponseEntity<LoginResponseDto> buildErrorResponse(
            HttpStatus status, String message) {

        return ResponseEntity
                .status(status)
                .body(new LoginResponseDto(message, null, null));
    }


}