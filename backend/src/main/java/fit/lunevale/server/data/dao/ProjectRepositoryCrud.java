package fit.lunevale.server.data.dao;


import fit.lunevale.server.data.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link Project} entities.
 * Extends {@link JpaRepository} to provide CRUD operations and custom query methods.
 */
@Repository
public interface ProjectRepositoryCrud extends JpaRepository<Project, Long> {

    /**
     * Finds all project IDs associated with a specific engineer ID.
     *
     * @param engineerId the ID of the engineer
     * @return a list of project IDs that the engineer is working on
     */
    @Query("SELECT p.id FROM Project p JOIN p.workingBy c WHERE c.id = :engineerId")
    List<Long> findAllWorkingProjectsId (Long engineerId);
}
