package com.jg.springsecurity.security;

import com.jg.springsecurity.filter.SecurityFilter;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 4. Create Config class extending WebSecurityConfigurerAdapter. @EnableWebSecurity, and override:
 * - configure(AuthenticationManagerBuilder auth)
 * - configure(HttpSecurity http)
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    /**
     * 5. Override authenticationProvider and userDetailsService to use.
     */
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider())
                .userDetailsService(userDetailsService());
    }

    /**
     * 6. Set http authorizeRequests mapping.
     * - anyMatchers("/actuator/*").permitAll() = Allow all endpointing matching /actuator/* without authentication.
     * - anyRequest().authenticated() = require any other request to be authenticated.
     * - and().formLogin() = provide login form to enter username and password.
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/actuato*").permitAll()
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                .and()
                    .addFilterAfter(new SecurityFilter(), SwitchUserFilter.class);
    }

    @Bean
    public MyUserDetailsServices userDetailsService() {
        return new MyUserDetailsServices(passwordEncoder);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        final MyAuthenticationProvider authProvider = new MyAuthenticationProvider(passwordEncoder);
        authProvider.setUserDetailsService(userDetailsService());
        return authProvider;
    }

    /**
     * 1. Create POJO implementing UserDetails. Map getAuthorities(), getUsername(), and getPassword() (encoded).
     */
    @Data
    @Slf4j
    @RequiredArgsConstructor
    static class MyUserDetails implements UserDetails {

        private final String username;
        private final String password;
        private final String[] roles;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            log.info("getAuthorities");
            return Arrays.stream(roles)
                    .map(role -> (GrantedAuthority) () -> role)
                    .collect(Collectors.toList());
        }

        @Override
        public String getPassword() {
            log.info("getPassword");
            return this.password;
        }

        @Override
        public String getUsername() {
            log.info("getUsername");
            return this.username;
        }

        @Override
        public boolean isAccountNonExpired() {
            log.info("isAccountNonExpired");
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            log.info("isAccountNonLocked");
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            log.info("isCredentialsNonExpired");
            return true;
        }

        @Override
        public boolean isEnabled() {
            log.info("isEnabled");
            return true;
        }
    }

    /**
     * 2. Create Service implementing UserDetailsService, overriding loadByUsername (fetch from DB?)
     * In this example, passwordEncoder is used to encode passwords (see @Bean PasswordEncoder).
     */
    @Slf4j
    @RequiredArgsConstructor
    static class MyUserDetailsServices implements UserDetailsService {

        private final PasswordEncoder passwordEncoder;

        @Override
        public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
            log.info("loadUserByUsername");
            final String[] roles = new String[3];
            roles[0] = "OBSERVER";
            roles[1] = "USER";
            roles[2] = "ADMIN";
            return new MyUserDetails(username, passwordEncoder.encode(username), roles);
        }

    }

    /**
     * 3. Create Service extending DaoAuthenticationProvider. This carries out checks and handling of authentication.
     */
    @Slf4j
    @RequiredArgsConstructor
    static class MyAuthenticationProvider extends DaoAuthenticationProvider {

        private final PasswordEncoder passwordEncoder;

        @Override
        protected void additionalAuthenticationChecks(final UserDetails userDetails, final UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
            log.info("additionalAuthenticationChecks");
            final String presentedPassword = authentication.getCredentials().toString();
            final boolean matches = passwordEncoder.matches(presentedPassword, userDetails.getPassword());

            if (!matches) {
                throw new RuntimeException("Invalid password.");
            }
        }
    }
}
