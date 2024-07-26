package fit.lunevale.server.data.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Building implements EntityWithId<Long> {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String building_type;
    @ManyToOne(optional = false)
    private Project inProject;


    public Building() {}

    public Building(Long id, String name, String building_type, Project inProject) {
        this.id = id;
        this.name = name;
        this.building_type = building_type;
        this.inProject = inProject;
    }

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

    public Project getInProject() {
        return inProject;
    }

    public void setInProject(Project inProject) {
        this.inProject = inProject;
    }
}
