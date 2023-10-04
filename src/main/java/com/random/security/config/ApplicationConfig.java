package com.random.security.config;

import com.random.security.account.Account;
import com.random.security.account.AccountRepository;
import com.random.security.account.AccountService;
import com.random.security.account.request.CreateAccountRequest;
import com.random.security.role.Role;
import com.random.security.role.RoleRepository;
import com.random.security.user.User;
import com.random.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;

    @Bean
    public CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, AccountService accountService) {
        return args -> {
            Role adminRole = Role.builder()
                    .id(1)
                    .name("ADMIN")
                    .build();
            Role accountantRole = Role.builder()
                    .id(2)
                    .name("ACCOUNTANT")
                    .build();
            Role userRole = Role.builder()
                    .id(3)
                    .name("USER")
                    .build();
            roleRepository.save(adminRole);
            roleRepository.save(accountantRole);
            roleRepository.save(userRole);

            List<Role> adminRoles = new ArrayList<>();
            adminRoles.add(adminRole);
            User admin = User.builder()
                    .name("admin")
                    .username("admin")
                    .roles(adminRoles)
                    .password(passwordEncoder.encode("admin"))
                    .build();

            List<Role> userRoles = new ArrayList<>();
            userRoles.add(userRole);
            User user = User.builder()
                    .name("user")
                    .username("user")
                    .roles(userRoles)
                    .password(passwordEncoder.encode("user"))
                    .build();
            List<Role> accountantRoles = new ArrayList<>();
            accountantRoles.add(accountantRole);
            User accountant = User.builder()
                    .name("accountant")
                    .username("accountant")
                    .roles(accountantRoles)
                    .password(passwordEncoder.encode("accountant"))
                    .build();
            userRepository.save(admin);
            userRepository.save(user);
            userRepository.save(accountant);
        };
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Nincs ilyen felhasználó"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    @Primary
    LocalValidatorFactoryBean localValidatorFactoryBean() {
        return new LocalValidatorFactoryBean();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
