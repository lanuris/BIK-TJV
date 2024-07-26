package fit.lunevale.server.presentation.datatransfer.dto;

public class BuildingDTO {

    private Long id;
    private String name;
    private String building_type;
    private ProjectDTO inProject;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuilding_type() {
        return building_type;
    }

    public void setBuilding_type(String building_type) {
        this.building_type = building_type;
    }

    public ProjectDTO getInProject() {
        return inProject;
    }

    public void setInProject(ProjectDTO inProject) {
        this.inProject = inProject;
    }

}
