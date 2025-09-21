package fr.btssio.komeet.api.security;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsUtils {

    private UserDetailsUtils() {
        // No property(ies) needed
        // If you need to add more, delete this comment
    }

    @Contract("_, _, _ -> new")
    public static @NotNull UserDetails of(final String username, final String password, final String role) {
        return User.builder()
                .username(username)
                .password(password)
                .roles(role)
                .build();
    }
}
