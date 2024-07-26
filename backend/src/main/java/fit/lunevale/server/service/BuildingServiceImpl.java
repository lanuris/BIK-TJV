package fit.lunevale.server.service;

import fit.lunevale.server.data.dao.BuildingRepositoryCrud;
import fit.lunevale.server.data.domain.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class BuildingServiceImpl extends CrudServiceImpl<Building, Long> implements BuildingService{
    protected BuildingRepositoryCrud buildingRepository;

    public BuildingServiceImpl(BuildingRepositoryCrud buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    @Override
    protected JpaRepository<Building, Long> getRepository() {
        return buildingRepository;
    }
}
