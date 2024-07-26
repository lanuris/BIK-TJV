package fit.lunevale.server.presentation.datatransfer.mapper;

import fit.lunevale.server.data.domain.Engineer;
import fit.lunevale.server.presentation.datatransfer.dto.EngineerCreateDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EngineerCreateMapper extends MapperImpl<Engineer, EngineerCreateDTO>{

    public EngineerCreateMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<EngineerCreateDTO> getDtoClass() {
        return EngineerCreateDTO.class;
    }

    @Override
    protected Class<Engineer> getEntityClass() {
        return Engineer.class;
    }
}
