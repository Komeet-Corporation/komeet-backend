package fr.btssio.komeet.komeetapi.config;

import fr.btssio.komeet.komeetapi.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
        return auth.build();
    }

    @Bean
    public SecurityFilterChain filterChain(@NotNull HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((auth) -> auth
                        .requestMatchers("**").permitAll()).authorizeHttpRequests((auth) -> auth
                        .requestMatchers(HttpMethod.GET, "/room").hasAnyRole(Role.USER.name(), Role.ADMIN.name(), Role.SUPER_ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/company/{email}").hasAnyRole(Role.USER.name(), Role.ADMIN.name(), Role.SUPER_ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/user/{email}").hasAnyRole(Role.USER.name(), Role.ADMIN.name(), Role.SUPER_ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/role/user").hasAnyRole(Role.UNKNOWN.name(), Role.USER.name(), Role.ADMIN.name(), Role.SUPER_ADMIN.name())
                        .requestMatchers(HttpMethod.PUT, "/user").hasAnyRole(Role.UNKNOWN.name(), Role.USER.name(), Role.ADMIN.name(), Role.SUPER_ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/user/favorite").hasAnyRole(Role.USER.name(), Role.ADMIN.name(), Role.SUPER_ADMIN.name())
                        .requestMatchers("/actuator").hasAnyRole(Role.SUPER_ADMIN.name()))
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    private enum Role {
        UNKNOWN, USER, ADMIN, SUPER_ADMIN
    }
}
