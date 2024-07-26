package fit.lunevale.server.controller;

import fit.lunevale.server.data.domain.Engineer;
import fit.lunevale.server.data.security.Role;
import fit.lunevale.server.presentation.controller.EngineerController;
import fit.lunevale.server.presentation.datatransfer.dto.EngineerCreateDTO;
import fit.lunevale.server.presentation.datatransfer.dto.EngineerDTO;
import fit.lunevale.server.presentation.datatransfer.mapper.EngineerCreateMapper;
import fit.lunevale.server.presentation.datatransfer.mapper.EngineerMapper;
import fit.lunevale.server.service.EngineerService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(EngineerController.class)
public class EngineerControllerTest {

    @Autowired
    EngineerController engineerController;

    @MockBean
    EngineerService engineerService;

    @MockBean
    EngineerMapper engineerMapper;

    @MockBean
    EngineerCreateMapper engineerCreateMapper;

    @Autowired
    MockMvc mockMvc;

    private EngineerCreateDTO engineerCreateDTO;
    private EngineerDTO engineerDTO;
    private Engineer engineer;

    @BeforeEach
    void setUp() {
        this.engineerCreateDTO = new EngineerCreateDTO();
        this.engineerCreateDTO.setName("John");
        this.engineerCreateDTO.setSurname("Dow");
        this.engineerCreateDTO.setEmail("super@gmail.com");
        this.engineerCreateDTO.setPassword("123456789");

        this.engineerDTO = new EngineerDTO();
        this.engineerDTO.setId(1L);
        this.engineerDTO.setName("John");
        this.engineerDTO.setSurname("Dow");
        this.engineerDTO.setEmail("super@gmail.com");
        this.engineerDTO.setPassword("123456789");

        this.engineer = new Engineer(1L,"John", "Dow", "super@gmail.com", "123456789", Role.User);
    }

    //GET ONE
    @Test
    @WithMockUser(username = "user")
    public void testGetOne() throws Exception {

        //For client with id "akorol6969@gmail.com"
        Mockito.when(engineerService.readById(1L)).thenReturn(engineer);
        Mockito.when(engineerMapper.toDTO(engineer)).thenReturn(engineerDTO);

        var neco = engineerService.readById(1L);
        //For existing client
        mockMvc.perform(MockMvcRequestBuilders.get("/engineer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("John")))
                .andExpect(jsonPath("$.surname", Matchers.is("Dow")))
                .andExpect(jsonPath("$.email", Matchers.is("super@gmail.com")));

    }
}
