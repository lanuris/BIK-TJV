package fit.lunevale.server.presentation;

import fit.lunevale.server.presentation.datatransfer.mapper.Mapper;
import fit.lunevale.server.service.CrudService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;



/**
 * AbstractControllerRest is an abstract base class designed to facilitate RESTful CRUD operations
 * for a specific entity type within a Spring MVC application.
 *
 * @param <S> The service interface type that provides CRUD operations.
 * @param <T> The entity type managed by the controller.
 * @param <DTO> The DTO (Data Transfer Object) type for representing entity data in responses.
 * @param <CreateDTO> The DTO type used for creating new entities.
 * @param <ID> The type of the entity's identifier (e.g., Long, Integer).
 */
public abstract class AbstractControllerRest<S extends CrudService<T, ID>, T, DTO, CreateDTO, ID> {

    protected abstract S getService();

    protected abstract Mapper<T, DTO> getMapper();

    protected abstract Mapper<T, CreateDTO> getCreateMapper();

    protected static final Logger logger = LoggerFactory.getLogger(AbstractControllerRest.class);

    /**
     * Handles HTTP POST requests to create a new entity.
     *
     * @param data The data used to create the entity.
     * @return ResponseEntity containing the created DTO with HTTP status CREATED (201) on success.
     * @throws ResponseStatusException with HTTP status CONFLICT (409) if creation fails due to conflicts.
     * @throws ResponseStatusException with HTTP status BAD_REQUEST (400) if creation fails due to other errors.
     */
    @PostMapping
    public ResponseEntity<DTO> create(@Valid @RequestBody CreateDTO data) throws ResponseStatusException {
        try {
            T createdEntity = getService().create(getCreateMapper().toEntity(data));
            DTO createdDTO = getMapper().toDTO(createdEntity);
            return new ResponseEntity<>(createdDTO, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Illegal argument exception while creating", e);
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } catch (Exception e) {
            logger.error("General exception while creating", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Handles HTTP DELETE requests to delete an entity by its ID.
     *
     * @param id The identifier of the entity to delete.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable ID id) {
        getService().deleteById(id);
    }

    /**
     * Handles HTTP GET requests to fetch all entities.
     *
     * @return Iterable of DTOs representing all entities.
     */
    @GetMapping
    @ResponseBody
    public Iterable<DTO> readAll() {
        return getMapper().toDTOList(getService().readAll());
    }

    /**
     * Handles HTTP GET requests to fetch an entity by its ID.
     *
     * @param id The identifier of the entity to fetch.
     * @return DTO representing the fetched entity.
     * @throws ResponseStatusException with HTTP status NOT_FOUND (404) if the entity is not found.
     */
    @GetMapping("/{id}")
    @ResponseBody
    public DTO readById(@PathVariable ID id) throws ResponseStatusException {

        try {
            return getMapper().toDTO(getService().readById(id));
        } catch (IllegalArgumentException e){
            logger.error("Illegal argument exception while searching by id", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Handles HTTP PUT requests to update an entity by its ID.
     *
     * @param data The data used to update the entity.
     * @param id   The identifier of the entity to update.
     * @return DTO representing the updated entity.
     * @throws ResponseStatusException with HTTP status NOT_FOUND (404) if the entity to update is not found.
     */
    @PutMapping("/{id}")
    @ResponseBody
    public DTO update(@Valid @RequestBody CreateDTO data, @PathVariable ID id) {
        try {
            T updatedEntity = getService().update(id, getCreateMapper().toEntity(data));
            return getMapper().toDTO(updatedEntity);
        } catch (IllegalArgumentException e) {
            logger.error("Illegal argument exception while updating", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}

