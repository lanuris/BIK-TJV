package fit.lunevale.server.data.dao;

import fit.lunevale.server.data.domain.Engineer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Engineer} entities.
 * Extends {@link JpaRepository} to provide CRUD operations and custom query methods.
 */
@Repository
public interface EngineerRepositoryCrud extends JpaRepository<Engineer, Long> {

    /**
     * Finds an engineer by their email.
     *
     * @param email the email of the engineer to find
     * @return an {@link Optional} containing the found engineer, or empty if no engineer was found
     */
    Optional<Engineer> findByEmail(String email);

    /**
     * Finds all engineers associated with a specific project ID.
     *
     * @param projectId the ID of the project
     * @return a list of engineers working on the project with the specified ID
     */
    @Query("SELECT c FROM Engineer c JOIN c.workingOn p WHERE p.id = :projectId")
    List<Engineer> findAllEngineerByProjectId(Long projectId);
}
