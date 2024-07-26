package fit.lunevale.client.client;

import fit.lunevale.client.model.building.BuildingCreateDTO;
import fit.lunevale.client.model.building.BuildingReadDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BuildingClient extends AbstractClient<BuildingCreateDTO, BuildingReadDTO> {

    public BuildingClient(@Value("${back_end_url}") String baseUrl) {

        super(baseUrl, "/building", BuildingReadDTO.class);
    }

}
