package fit.lunevale.server.service;

import fit.lunevale.server.data.dao.EngineerRepositoryCrud;
import fit.lunevale.server.data.dao.ProjectRepositoryCrud;
import fit.lunevale.server.data.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ProjectServiceImpl extends CrudServiceImpl<Project, Long> implements ProjectService{
    protected ProjectRepositoryCrud projectRepository;
    protected EngineerRepositoryCrud engineerRepository;

    public ProjectServiceImpl(ProjectRepositoryCrud projectRepository,
                              EngineerRepositoryCrud engineerRepository) {
        this.projectRepository = projectRepository;
        this.engineerRepository = engineerRepository;
    }

    @Override
    protected JpaRepository<Project, Long> getRepository() {
        return projectRepository;
    }

    public void updateFreelancers (Long engineerId, Long projectId)  {

        var engineerData = engineerRepository.findById(engineerId).orElseThrow(IllegalArgumentException::new);
        var projectData = projectRepository.findById(projectId).orElseThrow(IllegalArgumentException::new);
        var workingBy = projectData.getWorkingBy();
            if (workingBy.contains(engineerData)){
                workingBy.remove(engineerData);
            }
            else {
                workingBy.add(engineerData);
            }
        projectRepository.save(projectData);
    }

    public List<Long> readAllWorkingProjectsId (Long clientId){
        return projectRepository.findAllWorkingProjectsId(clientId);
    }

}
