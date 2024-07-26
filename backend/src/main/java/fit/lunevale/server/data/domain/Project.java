package fit.lunevale.server.data.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Collection;

@Entity
public class Project implements EntityWithId<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String type;
    @NotNull
    private Long price;

    @ManyToMany
    private Collection<Engineer> workingBy;


    @OneToMany(mappedBy = "inProject", orphanRemoval = true)
    @JsonIgnore
    private Collection<Building> projectBuildings;

    public Project(Long id, String name, String type, Long price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public Project() {}

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Collection<Engineer> getWorkingBy() {
        return workingBy;
    }

    public void setWorkingBy(Collection<Engineer> workingBy) {
        this.workingBy = workingBy;
    }

    public void setOrderedByOne(Collection<Engineer> orderedBy) {
        this.workingBy = orderedBy;
    }

    public Collection<Building> getProjectObjects() {
        return projectBuildings;
    }

    public void setProjectObjects(Collection<Building> projectBuildings) {
        this.projectBuildings = projectBuildings;
    }
}
