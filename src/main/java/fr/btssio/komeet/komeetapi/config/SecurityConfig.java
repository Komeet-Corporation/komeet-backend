package fr.btssio.komeet.komeetapi.config;

import fr.btssio.komeet.komeetapi.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    private static final String SUPER_ADMIN = "3";
    private static final String ADMIN = "2";
    private static final String USER = "1";
    private static final String UNKNOWN = "0";
    private final UserRepository userRepository;

    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager initSecurityUser() {
        List<UserDetails> userDetails = userRepository.findAll().stream()
                .map(user -> User.builder().username(user.getEmail())
                        .password(encoder().encode(user.getPassword()))
                        .roles(checkRole(user.getRole().getLevel()))
                        .build())
                .toList();
        return new InMemoryUserDetailsManager(userDetails);
    }

    @Bean
    public SecurityFilterChain filterChain(@NotNull HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((auth) -> auth
                        .requestMatchers(HttpMethod.GET, "/room").hasAnyRole(USER, ADMIN, SUPER_ADMIN)
                        .requestMatchers(HttpMethod.GET, "/company/{email}").hasAnyRole(USER, ADMIN, SUPER_ADMIN)
                        .requestMatchers(HttpMethod.GET, "/user/{email}").hasAnyRole(USER, ADMIN, SUPER_ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/user").hasAnyRole(UNKNOWN, USER, ADMIN, SUPER_ADMIN)
                        .requestMatchers(HttpMethod.POST, "/user/favorite").hasAnyRole(USER, ADMIN, SUPER_ADMIN))
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    private String checkRole(Long level) {
        String[] roles = {SUPER_ADMIN, ADMIN, USER, UNKNOWN};
        return Arrays.stream(roles)
                .filter(role -> role.equals(level.toString()))
                .findFirst()
                .orElse(UNKNOWN);
    }

}
