package fit.lunevale.server.presentation.datatransfer.mapper;

import fit.lunevale.server.data.domain.Project;
import org.modelmapper.ModelMapper;
import fit.lunevale.server.presentation.datatransfer.dto.ProjectCreateDTO;
import org.springframework.stereotype.Component;

@Component
public class ProjectCreateMapper extends MapperImpl<Project, ProjectCreateDTO>{

    public ProjectCreateMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<ProjectCreateDTO> getDtoClass() {
        return ProjectCreateDTO.class;
    }

    @Override
    protected Class<Project> getEntityClass() {
        return Project.class;
    }
}
