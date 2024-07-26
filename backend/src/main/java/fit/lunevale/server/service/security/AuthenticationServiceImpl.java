package fit.lunevale.server.service.security;

import fit.lunevale.server.data.dao.EngineerRepositoryCrud;
import fit.lunevale.server.data.domain.Engineer;
import fit.lunevale.server.data.security.Role;
import fit.lunevale.server.data.security.response.JwtAuthenticationResponse;
import fit.lunevale.server.service.exception.DuplicateEmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final EngineerRepositoryCrud engineerRepositoryCrud;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    protected static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    public AuthenticationServiceImpl(EngineerRepositoryCrud engineerRepositoryCrud, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.engineerRepositoryCrud = engineerRepositoryCrud;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    @Override
    public JwtAuthenticationResponse signup(Engineer request) throws DuplicateEmailException{
        try {
            request.setPassword(passwordEncoder.encode(request.getPassword()));
            request.setRole(Role.User);
            engineerRepositoryCrud.save(request);
            var jwt = jwtService.generateToken(request);
            return JwtAuthenticationResponse.builder().token(jwt).build();
        }
        catch (DataIntegrityViolationException e) {
            logger.error("Data Integrity Violation Exception while signup " + e);
            throw new DuplicateEmailException("Email already exists: " + request.getEmail());
        }
    }

    @Override
    public JwtAuthenticationResponse signin (String usernameMail, String password) throws IllegalArgumentException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usernameMail, password));
        var user = engineerRepositoryCrud.findByEmail(usernameMail)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public Long identifyId (String token) throws IllegalArgumentException{
        token = token.replace("Bearer ", "");
       var engineer = engineerRepositoryCrud.findByEmail(jwtService
               .extractUserName(token)).orElseThrow(() -> new IllegalArgumentException("Invalid token"));
       return engineer.getId();
    }
}
