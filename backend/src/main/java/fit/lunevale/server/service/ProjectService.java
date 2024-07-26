package fit.lunevale.server.service;

import fit.lunevale.server.data.domain.Project;

import java.util.List;

/**
 * ProjectService interface that extends the CrudService interface for managing Project entities.
 */
public interface ProjectService extends CrudService<Project, Long>{

    /**
     * Updates the freelancers associated with the given project based on the provided client data.
     * This method allows for adding, removing, or modifying freelancers assigned to a project as per
     * the client’s requirements.
     *
     * @param engineerId the client id containing information required for updating freelancers
     * @param projectId the project id specifying the project whose freelancers need to be updated
     * @throws IllegalArgumentException if the engineer or project does not exist
     */
    void updateFreelancers (Long engineerId, Long projectId) throws IllegalArgumentException;

    /**
     * Retrieves a list of project IDs associated with a specific engineer.
     *
     * @param engineerId the ID of the engineer whose project IDs are to be retrieved.
     * @return a list of project IDs that the engineer is working on.
     */
    List<Long> readAllWorkingProjectsId (Long engineerId);
}
