package fit.lunevale.server.data.dao;

import fit.lunevale.server.data.domain.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository interface for managing {@link Building} entities.
 * Extends {@link JpaRepository} to provide CRUD operations and custom query methods.
 */
@Repository
public interface BuildingRepositoryCrud extends JpaRepository<Building, Long> {

}
