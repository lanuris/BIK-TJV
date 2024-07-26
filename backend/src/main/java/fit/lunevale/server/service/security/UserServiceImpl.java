package fit.lunevale.server.service.security;

import fit.lunevale.server.data.dao.EngineerRepositoryCrud;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final EngineerRepositoryCrud engineerRepositoryCrud;

    public UserServiceImpl(EngineerRepositoryCrud engineerRepositoryCrud) {
        this.engineerRepositoryCrud = engineerRepositoryCrud;
    }

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return engineerRepositoryCrud.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }
}
