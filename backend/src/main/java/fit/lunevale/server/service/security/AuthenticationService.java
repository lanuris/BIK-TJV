package fit.lunevale.server.service.security;


import fit.lunevale.server.data.domain.Engineer;
import fit.lunevale.server.data.security.response.JwtAuthenticationResponse;
import fit.lunevale.server.service.exception.DuplicateEmailException;
import org.springframework.stereotype.Service;

/**
 * Service interface for handling authentication-related operations.
 * Provides methods for user signup, signin, and token-based identification.
 */
@Service
public interface AuthenticationService {

    /**
     * Registers a new engineer with the provided details.
     *
     * @param request the {@link Engineer} object containing the signup details.
     * @return a {@link JwtAuthenticationResponse} containing the JWT token.
     * @throws DuplicateEmailException if an engineer with the same email already exists
     */
    JwtAuthenticationResponse signup(Engineer request) throws DuplicateEmailException;

    /**
     * Authenticates a user with the provided username/email and password.
     *
     * @param usernameMail the username or email of the user attempting to sign in.
     * @param password the password of the user attempting to sign in.
     * @return a {@link JwtAuthenticationResponse} containing the JWT token.
     * @throws IllegalArgumentException if the email or password is invalid
     */
    JwtAuthenticationResponse signin(String usernameMail, String password) throws IllegalArgumentException;

    /**
     * Identifies the user ID associated with the provided JWT token.
     *
     * @param token the JWT token to be decoded.
     * @return the ID of the user associated with the provided token.
     * @throws IllegalArgumentException if the token is invalid
     */
    Long identifyId (String token) throws IllegalArgumentException;
}
