package fit.lunevale.server.service.security;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Service interface for handling user-related operations.
 * Provides a method to access the {@link UserDetailsService} for loading user-specific data.
 */
public interface UserService {

    /**
     * Provides an implementation of {@link UserDetailsService} for loading user-specific data.
     *
     * @return an instance of {@link UserDetailsService}.
     */
    UserDetailsService userDetailsService();
}
