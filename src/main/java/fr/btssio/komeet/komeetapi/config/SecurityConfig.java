package fr.btssio.komeet.komeetapi.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    public SecurityConfig() {
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
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
