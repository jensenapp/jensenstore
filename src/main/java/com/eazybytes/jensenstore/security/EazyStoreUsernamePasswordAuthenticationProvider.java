package com.eazybytes.jensenstore.security;

import com.eazybytes.jensenstore.entity.Customer;
import com.eazybytes.jensenstore.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EazyStoreUsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    
    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String pwd = authentication.getCredentials().toString();

        Customer customer = customerRepository.findByEmail(name).orElseThrow(() -> new UsernameNotFoundException("User details not found for the user:"+name));

        if (passwordEncoder.matches(pwd,customer.getPasswordHash())){
            return new UsernamePasswordAuthenticationToken(customer,null, Collections.emptyList());
        }else {
            throw new BadCredentialsException("Invalid password!");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
