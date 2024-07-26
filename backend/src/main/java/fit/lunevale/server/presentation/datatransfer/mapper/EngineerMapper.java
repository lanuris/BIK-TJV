package fit.lunevale.server.presentation.datatransfer.mapper;

import fit.lunevale.server.data.domain.Engineer;
import fit.lunevale.server.presentation.datatransfer.dto.EngineerDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EngineerMapper extends MapperImpl<Engineer, EngineerDTO>{

    public EngineerMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<EngineerDTO> getDtoClass() {
        return EngineerDTO.class;
    }

    @Override
    protected Class<Engineer> getEntityClass() {
        return Engineer.class;
    }
}
