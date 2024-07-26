package fit.lunevale.server.data;

import fit.lunevale.server.data.dao.EngineerRepositoryCrud;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Component that loads initial data into the database.
 */
@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {


    private EngineerRepositoryCrud engineerRepositoryCrud;
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

            var list = engineerRepositoryCrud.findAll();
            if (!list.isEmpty()){
                list.forEach(entity -> {
                    if (!passwordEncoder.matches("admin", entity.getPassword())) { // Assuming a dummy plain text password
                        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
                        engineerRepositoryCrud.save(entity);
                    }
                });
            }

    }
}
