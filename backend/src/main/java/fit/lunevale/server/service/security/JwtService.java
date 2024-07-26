package fit.lunevale.server.service.security;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Service interface for handling JWT operations.
 * Provides methods for extracting username, generating tokens, and validating tokens.
 */
public interface JwtService {

    /**
     * Extracts the username from the provided JWT token.
     *
     * @param token the JWT token from which to extract the username.
     * @return the username extracted from the token.
     */
    String extractUserName(String token);

    /**
     * Generates a JWT token for the given user details.
     *
     * @param userDetails the {@link UserDetails} object containing the user's information.
     * @return the generated JWT token.
     */
    String generateToken(UserDetails userDetails);

    /**
     * Validates the provided JWT token against the given user details.
     *
     * @param token the JWT token to be validated.
     * @param userDetails the {@link UserDetails} object containing the user's information.
     * @return {@code true} if the token is valid, {@code false} otherwise.
     */
    boolean isTokenValid(String token, UserDetails userDetails);
}
