package fit.lunevale.server.presentation.controller;

import fit.lunevale.server.data.domain.Building;
import fit.lunevale.server.presentation.AbstractControllerRest;
import fit.lunevale.server.presentation.datatransfer.dto.*;
import fit.lunevale.server.presentation.datatransfer.mapper.Mapper;
import fit.lunevale.server.service.BuildingService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * BuildingController is a REST controller for managing Building entities.
 * It extends the AbstractControllerRest class to provide standard CRUD operations.
 *
 * <p>This controller handles HTTP requests for the /building endpoint and produces JSON responses.</p>
 *
 * @see AbstractControllerRest
 */
@RestController
@RequestMapping(value = "/building", produces = MediaType.APPLICATION_JSON_VALUE)
public class BuildingController extends AbstractControllerRest<BuildingService, Building, BuildingDTO, BuildingCreateDTO, Long> {

    BuildingService buildingService;
    Mapper<Building, BuildingDTO> buildingMapper;
    Mapper<Building, BuildingCreateDTO> buildingCreateMapper;

    public BuildingController(BuildingService buildingService,
                              Mapper<Building, BuildingDTO> buildingMapper,
                              Mapper<Building, BuildingCreateDTO> buildingCreateMapper) {
        this.buildingService = buildingService;
        this.buildingMapper = buildingMapper;
        this.buildingCreateMapper = buildingCreateMapper;
    }

    @Override
    protected BuildingService getService() {
        return this.buildingService;
    }

    @Override
    protected Mapper<Building, BuildingDTO> getMapper() {
        return this.buildingMapper;
    }

    @Override
    protected Mapper<Building, BuildingCreateDTO> getCreateMapper() {
        return this.buildingCreateMapper;
    }
}
