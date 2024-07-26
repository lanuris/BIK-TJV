package fit.lunevale.server.dao;


import fit.lunevale.server.data.dao.EngineerRepositoryCrud;
import fit.lunevale.server.data.domain.Engineer;
import fit.lunevale.server.data.security.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EngineerRepositoryTest {

    @Autowired
    EngineerRepositoryCrud clientRepository;

    @Test
    public void testCreateReadDelete() {
        Engineer engineer = new Engineer(1L,"John", "Doe", "super@gmail.com","123456789", Role.Admin);

        clientRepository.save(engineer);
        List<Engineer> engineers = clientRepository.findAll();
        Assertions.assertThat(engineers).extracting(Engineer::getEmail).containsOnly("super@gmail.com");
        clientRepository.deleteAll();
        Assertions.assertThat(clientRepository.findAll()).isEmpty();
    }
}
