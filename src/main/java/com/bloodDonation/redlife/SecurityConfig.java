package com.bloodDonation.redlife;

import com.bloodDonation.redlife.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService(CustomUserDetailsService customUserDetailsService) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin").password("admin123").roles("Admin").build());
        return username -> {
            if ("admin".equals(username)) {
                return manager.loadUserByUsername(username);
            } else {
                return customUserDetailsService.loadUserByUsername(username);
            }
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/admin-login", "/user-login", "/register", "/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/admin-dashboard").hasRole("Admin")
                .requestMatchers("/donor-dashboard").hasRole("Donor")
                .requestMatchers("/receiver-dashboard").hasRole("Receiver")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login").loginProcessingUrl("/login")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                        String role = userDetails.getAuthorities().iterator().next().getAuthority();
                        if ("ROLE_Admin".equalsIgnoreCase(role)) {
                            response.sendRedirect("/admin-dashboard");
                        } else if ("ROLE_Donor".equalsIgnoreCase(role)) {
                            response.sendRedirect("/donor-dashboard");
                        } else if ("ROLE_Receiver".equalsIgnoreCase(role)) {
                            response.sendRedirect("/receiver-dashboard");
                        } else {
                            response.sendRedirect("/");
                        }
                    }
                })
                .permitAll()
            )
            .logout(logout -> logout.permitAll())
            .userDetailsService(userDetailsService);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // For demo only. Use BCryptPasswordEncoder in production!
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
} 