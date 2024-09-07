package fr.btssio.komeet.komeetapi.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(@NotNull HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((auth) -> auth
                        .requestMatchers("**").permitAll()).authorizeHttpRequests((auth) -> auth
                        .requestMatchers(HttpMethod.GET, "/role/user").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/user").permitAll()
                        .requestMatchers(HttpMethod.GET, "/room").hasAnyRole(Role.USER.name(), Role.ADMIN.name(), Role.SUPER_ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/company/{email}").hasAnyRole(Role.USER.name(), Role.ADMIN.name(), Role.SUPER_ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/user/{email}").hasAnyRole(Role.USER.name(), Role.ADMIN.name(), Role.SUPER_ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/user/favorite").hasAnyRole(Role.USER.name(), Role.ADMIN.name(), Role.SUPER_ADMIN.name())
                        .requestMatchers("/actuator").hasAnyRole(Role.SUPER_ADMIN.name()))
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    private enum Role {
        USER, ADMIN, SUPER_ADMIN
    }
}
