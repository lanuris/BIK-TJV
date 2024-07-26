package fit.lunevale.server.presentation.datatransfer.mapper;

import fit.lunevale.server.data.domain.Building;
import fit.lunevale.server.presentation.datatransfer.dto.BuildingCreateDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BuildingCreateMapper extends MapperImpl<Building, BuildingCreateDTO> {

    public BuildingCreateMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<BuildingCreateDTO> getDtoClass() {
        return BuildingCreateDTO.class;
    }

    @Override
    protected Class<Building> getEntityClass() {
        return Building.class;
    }

    @Override
    public Building toEntity(BuildingCreateDTO postDTO) {

        //Configure ModelMapper only once
        if (!modelMapper.getTypeMaps().stream()
                .anyMatch(tm -> tm.getSourceType().equals(BuildingCreateDTO.class) && tm.getDestinationType().equals(Building.class))) {

            modelMapper.typeMap(BuildingCreateDTO.class, Building.class).addMappings(mapper -> {
                mapper.skip(Building::setId); // Skip the direct mapping for id
            });
        }

        return modelMapper.map(postDTO, Building.class);
    }
}
