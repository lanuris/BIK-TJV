package fit.lunevale.server.presentation;

import fit.lunevale.server.data.domain.Engineer;
import fit.lunevale.server.data.security.response.JwtAuthenticationResponse;
import fit.lunevale.server.presentation.datatransfer.dto.EngineerCreateDTO;
import fit.lunevale.server.presentation.datatransfer.dto.EngineerLoginDTO;
import fit.lunevale.server.presentation.datatransfer.mapper.Mapper;
import fit.lunevale.server.service.security.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * REST controller for handling authentication-related requests.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final Mapper<Engineer, EngineerCreateDTO> engineerCreateMapper;
    protected static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    /**
     * Handles the signup process for a new engineer.
     *
     * @param request  the data transfer object containing the engineer's details
     * @param response the HTTP servlet response
     * @return a response entity with a success message
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid EngineerCreateDTO request, HttpServletResponse response) {
            var respToken = authenticationService.signup(engineerCreateMapper.toEntity(request));
            var cookie = createCookie(respToken);
            response.addCookie(cookie);
            return ResponseEntity.ok("Registration successful");
    }

    /**
     * Handles the signin process for an engineer.
     *
     * @param request  the data transfer object containing the engineer's login details
     * @param response the HTTP servlet response
     * @return a response entity with a success message
     * @throws ResponseStatusException if the email or password is invalid
     */
    @PostMapping("/signin")
    public ResponseEntity<String> signin(@RequestBody EngineerLoginDTO request, HttpServletResponse response) {
        try {
            var respToken = authenticationService.signin(request.getEmail(), request.getPassword());
            var cookie = createCookie(respToken);
            response.addCookie(cookie);
            return ResponseEntity.ok("Login successful");
        } catch (IllegalArgumentException e){
            logger.error("Illegal Argument Exception while signin ", e);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Identifies the engineer's ID based on the provided JWT token.
     *
     * @param request the HTTP servlet request containing the JWT token
     * @return a response entity with the engineer's ID
     * @throws ResponseStatusException if the token is invalid
     */
    @GetMapping("/id")
    public ResponseEntity<Long> identifyId (HttpServletRequest request) {
        try {
            var token = request.getHeader(HttpHeaders.AUTHORIZATION);
            return ResponseEntity.ok(authenticationService.identifyId(token));
        }
        catch (IllegalArgumentException e) {
            logger.error("Illegal argument exception while identifying id ", e);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

    }

    /**
     * Creates a secure HTTP-only cookie containing the JWT token.
     *
     * @param respToken the JWT authentication response containing the token
     * @return the created cookie
     */
    private Cookie createCookie (JwtAuthenticationResponse respToken){

        Cookie cookie = new Cookie("jwt", respToken.getToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // Ensure this is only over HTTPS in production
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60); // 1 hour
        return cookie;
    }
}

