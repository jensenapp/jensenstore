package com.eazybytes.jensenstore.security;

import com.eazybytes.jensenstore.entity.Customer;
import com.eazybytes.jensenstore.entity.Role;
import com.eazybytes.jensenstore.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

        Set<Role> roles = customer.getRoles();

        List<GrantedAuthority> authorities=roles.
                stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

        if (passwordEncoder.matches(pwd,customer.getPasswordHash())){
            return new UsernamePasswordAuthenticationToken(customer,null, authorities);
        }else {
            throw new BadCredentialsException("Invalid password!");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
