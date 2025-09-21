package fr.btssio.komeet.api.config;

import fr.btssio.komeet.api.security.RoleEnum;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(@NotNull HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.PUT, "/user").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/verify").permitAll()
                        .requestMatchers(HttpMethod.GET, "/room").permitAll()
                        .requestMatchers(HttpMethod.GET, "/role/user").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/{email}").hasAnyRole(RoleEnum.USER.name(), RoleEnum.ADMIN.name(), RoleEnum.SUPER_ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/user/favorite").hasAnyRole(RoleEnum.USER.name(), RoleEnum.ADMIN.name(), RoleEnum.SUPER_ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/company/{email}").hasAnyRole(RoleEnum.USER.name(), RoleEnum.ADMIN.name(), RoleEnum.SUPER_ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/actuator/**").hasAnyRole(RoleEnum.SUPER_ADMIN.name()))
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
