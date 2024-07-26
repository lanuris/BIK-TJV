package fit.lunevale.server.presentation.controller;

import fit.lunevale.server.data.domain.Project;
import fit.lunevale.server.presentation.AbstractControllerRest;
import fit.lunevale.server.presentation.datatransfer.dto.ProjectCreateDTO;
import fit.lunevale.server.presentation.datatransfer.dto.ProjectDTO;
import fit.lunevale.server.presentation.datatransfer.mapper.Mapper;
import fit.lunevale.server.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * ProjectController is a REST controller for managing Project entities.
 * It extends the AbstractControllerRest class to provide standard CRUD operations.
 *
 * <p>This controller handles HTTP requests for the /project endpoint and produces JSON responses.</p>
 *
 * @see AbstractControllerRest
 */
@RestController
@RequestMapping(value = "/project", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectController extends AbstractControllerRest<ProjectService, Project, ProjectDTO, ProjectCreateDTO, Long> {

    ProjectService projectService;

    Mapper<Project, ProjectDTO> projectMapper;
    Mapper<Project, ProjectCreateDTO> projectCreateMapper;

    public ProjectController(ProjectService projectService,
                             Mapper<Project, ProjectDTO> projectMapper,
                             Mapper<Project, ProjectCreateDTO> projectCreateMapper) {
        this.projectService = projectService;
        this.projectMapper = projectMapper;
        this.projectCreateMapper = projectCreateMapper;
    }

    @Override
    protected ProjectService getService() {
        return this.projectService;
    }

    @Override
    protected Mapper<Project, ProjectDTO> getMapper() {
        return this.projectMapper;
    }

    @Override
    protected Mapper<Project, ProjectCreateDTO> getCreateMapper() {
        return this.projectCreateMapper;
    }

    /**
     * Updates the freelancers (engineers) associated with a specific project.
     *
     * @param engineerId the ID of the engineer
     * @param projectId  the ID of the project
     * @throws ResponseStatusException if the engineer or project does not exist
     */
    @PutMapping("/update/working/{projectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateFreelancers(@RequestBody Long engineerId, @PathVariable Long projectId) {

        try {
            projectService.updateFreelancers(engineerId, projectId);
        } catch (IllegalArgumentException e){
            logger.error("Illegal argument exception while updating Freelancers", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves all project IDs for a given engineer.
     *
     * @param id the ID of the engineer
     * @return a list of project IDs that the engineer is working on.
     * @throws ResponseStatusException if an error occurs
     */
    @GetMapping("/ordered/{id}")
    @ResponseBody
    public List<Long> getAllWorkingProjectsId (@PathVariable Long id) throws ResponseStatusException {
        return projectService.readAllWorkingProjectsId(id);
    }

}
