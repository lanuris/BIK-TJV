package fit.lunevale.server.presentation.controller;
import fit.lunevale.server.data.domain.Engineer;
import fit.lunevale.server.presentation.AbstractControllerRest;
import fit.lunevale.server.presentation.datatransfer.dto.EngineerCreateDTO;
import fit.lunevale.server.presentation.datatransfer.dto.EngineerDTO;
import fit.lunevale.server.presentation.datatransfer.mapper.Mapper;
import fit.lunevale.server.service.EngineerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * EngineerController is a REST controller for managing Engineer entities.
 * It extends the AbstractControllerRest class to provide standard CRUD operations.
 *
 * <p>This controller handles HTTP requests for the /engineer endpoint and produces JSON responses.</p>
 *
 * @see AbstractControllerRest
 */
@RestController
@RequestMapping(value = "/engineer", produces = MediaType.APPLICATION_JSON_VALUE)
public class EngineerController extends AbstractControllerRest<EngineerService, Engineer, EngineerDTO, EngineerCreateDTO, Long> {

    private final EngineerService engineerService;
    private final Mapper<Engineer, EngineerDTO> engineerMapper;
    private final Mapper<Engineer, EngineerCreateDTO> engineerCreateMapper;


    public EngineerController(EngineerService engineerService, Mapper<Engineer, EngineerDTO> clientMapper, Mapper<Engineer, EngineerCreateDTO> clientCreateMapper) {
        this.engineerService = engineerService;
        this.engineerMapper = clientMapper;
        this.engineerCreateMapper = clientCreateMapper;
    }

    @Override
    protected EngineerService getService() {
        return this.engineerService;
    }

    @Override
    protected Mapper<Engineer, EngineerDTO> getMapper() {
        return this.engineerMapper;
    }

    @Override
    protected Mapper<Engineer, EngineerCreateDTO> getCreateMapper() {
        return this.engineerCreateMapper;
    }

    /**
     * Retrieves an Engineer by their email.
     *
     * @param email the email of the engineer
     * @return the engineer DTO
     * @throws ResponseStatusException if the engineer is not found
     */
    @GetMapping("/email")
    public EngineerDTO readByEmail(@RequestParam("email") String email) throws ResponseStatusException {
        try {
            var optClient = engineerService.findByEmail(email);
            return engineerMapper.toDTO(optClient);
        } catch (IllegalArgumentException e)  {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves all engineers by project ID.
     *
     * @param id the ID of the project
     * @return a list of engineer DTOs
     * @throws ResponseStatusException if an error occurs
     */
    @GetMapping("/ordered/{id}")
    @ResponseBody
    public List<EngineerDTO> getAllClientsByProjectId (@PathVariable Long id) throws ResponseStatusException {
        return engineerMapper.toDTOList(engineerService.readAllEngineersByProjectId(id));
    }
}
