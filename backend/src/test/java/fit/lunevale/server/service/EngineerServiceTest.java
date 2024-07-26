package fit.lunevale.server.service;

import fit.lunevale.server.data.dao.EngineerRepositoryCrud;
import fit.lunevale.server.data.domain.Engineer;
import fit.lunevale.server.data.security.Role;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;


import static org.mockito.Mockito.*;

@SpringBootTest
public class EngineerServiceTest {

    @Autowired
    EngineerService engineerService;

    @MockBean
    EngineerRepositoryCrud engineerRepositoryCrud;

    @MockBean
    private EntityManager entityManager;

    Engineer engineerEntity;

    @BeforeEach
    void setUp(){
        this.engineerEntity = new Engineer(1L,"John", "Dow", "super@gmail.com", "123456789", Role.User);
    }


    @Test
    void testReadByIdFound() {
        Long id = 1L;
        when(engineerRepositoryCrud.findById(id)).thenReturn(Optional.of(engineerEntity));

        Engineer result = engineerService.readById(id);

        Assertions.assertEquals(engineerEntity, result);
    }

    @Test
    void testReadByIdNotFound() {
        Long id = 1L;
        when(engineerRepositoryCrud.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> engineerService.readById(1L));
    }

    @Test
    public void testReadAll() {
        Engineer secondEngineerEntity = new Engineer(2L, "Jane", "Dowel", "lampa@gmail.com", "12348910", Role.User);

        List<Engineer> engineers = List.of(engineerEntity, secondEngineerEntity);

        Mockito.when(engineerRepositoryCrud.findAll()).thenReturn(engineers);

        Iterable<Engineer> result = engineerService.readAll();

        long size = StreamSupport.stream(result.spliterator(), false).count();

        Assertions.assertEquals(2, size);
        Mockito.verify(engineerRepositoryCrud, Mockito.times(1)).findAll();
    }

    @Test
    public void testCreate() {

        when(engineerRepositoryCrud.save(engineerEntity)).thenReturn(engineerEntity);

        engineerService.create(engineerEntity);
        verify(engineerRepositoryCrud, times(1)).save(engineerEntity);
    }

    @Test
    void updateTestNotOk() {
        Long id = 1L;
        Mockito.when(engineerRepositoryCrud.findById(id)).thenReturn(Optional.of(engineerEntity));

        engineerEntity.setName("UPDATE");

        Mockito.when(engineerRepositoryCrud.save(engineerEntity)).thenReturn(engineerEntity);

        Engineer result = engineerService.update(engineerEntity);

        Mockito.verify(engineerRepositoryCrud).save(engineerEntity);
        Assertions.assertEquals(engineerEntity, result);
    }

}
