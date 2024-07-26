package fit.lunevale.client.model.building;

public class BuildingCreateDTO {
    private String name;
    private String building_type;

    private Long inProjectId;

    public BuildingCreateDTO(String name, String building_type, Long inProjectId) {
        this.name = name;
        this.building_type = building_type;
        this.inProjectId = inProjectId;
    }

    public BuildingCreateDTO(Long id, String name, String building_type) {
        this.name = name;
        this.building_type = building_type;
    }

    public BuildingCreateDTO() {}


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

    public Long getInProjectId() {
        return inProjectId;
    }

    public void setInProjectId(Long inProjectId) {
        this.inProjectId = inProjectId;
    }
}
