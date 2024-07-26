package fit.lunevale.server.presentation.datatransfer.mapper;

import fit.lunevale.server.data.domain.Project;
import fit.lunevale.server.presentation.datatransfer.dto.ProjectDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper extends MapperImpl<Project, ProjectDTO>{

    public ProjectMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<ProjectDTO> getDtoClass() {
        return ProjectDTO.class;
    }

    @Override
    protected Class<Project> getEntityClass() {
        return Project.class;
    }
}
