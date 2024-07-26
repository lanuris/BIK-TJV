package fit.lunevale.client.model.project;

public class ProjectCreateDTO {

    private String name;
    private String type;
    private Long price;


    public ProjectCreateDTO ( String name, String type, Long price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public ProjectCreateDTO() {}

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

}
