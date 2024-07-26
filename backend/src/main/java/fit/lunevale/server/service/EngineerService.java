package fit.lunevale.server.service;

import fit.lunevale.server.data.domain.Engineer;

import java.util.List;

/**
 * EngineerService interface that extends the CrudService interface for managing Engineer entities.
 */
public interface EngineerService extends CrudService<Engineer, Long>{

    /**
     * Finds an Engineer entity based on the provided email address.
     *
     * @param email the email address of the Engineer to be found.
     * @return the Engineer entity matching the provided email address.
     * @throws IllegalArgumentException if the Engineer entity with the specified email is not found.
     */
    Engineer findByEmail(String email) throws IllegalArgumentException;

    /**
     * Retrieves a list of Engineer entities associated with a specific project ID.
     *
     * @param projectId the ID of the project whose associated engineers are to be retrieved.
     * @return a list of Engineer entities associated with the specified project ID.
     */
    List<Engineer> readAllEngineersByProjectId (Long projectId);

}
