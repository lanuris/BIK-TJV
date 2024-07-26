package fit.lunevale.client.model.building;

import fit.lunevale.client.model.project.ProjectDTO;

public class BuildingReadDTO extends BuildingDTO<ProjectDTO>{

    public BuildingCreateDTO convertToCreateDTO (){
        return new BuildingCreateDTO(this.getName(), this.getBuilding_type(), this.getInProject().getId());
    }
}
