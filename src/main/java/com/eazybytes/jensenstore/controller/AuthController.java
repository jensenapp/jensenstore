package com.eazybytes.jensenstore.controller;

import com.eazybytes.jensenstore.dto.LoginRequestDto;
import com.eazybytes.jensenstore.dto.LoginResponseDto;
import com.eazybytes.jensenstore.dto.RegisterRequestDto;
import com.eazybytes.jensenstore.dto.UserDto;
import com.eazybytes.jensenstore.entity.Customer;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequestDto registerRequestDto) {

        Customer customer = new Customer();
        BeanUtils.copyProperties(registerRequestDto,customer);
        customer.setPasswordHash(registerRequestDto.getPassword());
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

            var userDto = new UserDto();

            var loggedInUser = (User) authentication.getPrincipal(); // 取得登入者詳細資訊
            userDto.setName(loggedInUser.getUsername()); // 僅設定名稱，不含敏感資訊

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