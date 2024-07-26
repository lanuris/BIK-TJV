package fit.lunevale.client.model.building;

public class BuildingDTO<T> {

    private Long id = 0L;
    private String name;
    private String building_type;

    private T inProject = null;

    public BuildingDTO(Long id, String name, String building_type) {
        this.id = id;
        this.name = name;
        this.building_type = building_type;
    }

    public BuildingDTO(Long id, String name, String building_type, T inProject) {
        this.id = id;
        this.name = name;
        this.building_type = building_type;
        this.inProject = inProject;
    }

    public BuildingDTO() {}

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

    public T getInProject() {
        return inProject;
    }

    public void setInProject(T inProject) {
        this.inProject = inProject;
    }


}
