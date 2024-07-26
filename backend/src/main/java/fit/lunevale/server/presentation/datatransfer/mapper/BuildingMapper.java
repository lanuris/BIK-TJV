package fit.lunevale.server.presentation.datatransfer.mapper;

import fit.lunevale.server.data.domain.Building;
import fit.lunevale.server.presentation.datatransfer.dto.BuildingDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BuildingMapper extends MapperImpl<Building, BuildingDTO> {

    public BuildingMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<BuildingDTO> getDtoClass() {
        return BuildingDTO.class;
    }

    @Override
    protected Class<Building> getEntityClass() {
        return Building.class;
    }
}
