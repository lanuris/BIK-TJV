package fit.lunevale.client.model.project;

public class ProjectDTO {

    private Long id = 0L;
    private String name;
    private String type;
    private Long price;


    public ProjectDTO(Long id, String name, String type, Long price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
    }


    public ProjectDTO() {}

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

    public ProjectCreateDTO convertToCreateDTO (){
        return new ProjectCreateDTO(this.getName(), this.getType(), this.getPrice());
    }

}
