 package fit.lunevale.client.ui;

import fit.lunevale.client.client.BuildingClient;
import fit.lunevale.client.client.ProjectClient;
import fit.lunevale.client.model.building.BuildingCreateDTO;
import fit.lunevale.client.model.building.BuildingReadDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
 public class BuildingWebController {

    private final BuildingClient buildingClient;
    private final ProjectClient projectClient;

    public BuildingWebController(BuildingClient buildingClient, ProjectClient projectClient) {
        this.buildingClient = buildingClient;
        this.projectClient = projectClient;
    }

    @GetMapping("/buildings")
    public String listAllBuildings (Model model, HttpServletRequest request) {

        try{
            var securityCookie = AuthWebController.getSecurityCookie(request);
            model.addAttribute("buildingAll", buildingClient.readAll(securityCookie.getValue()).collectList().block());
            model.addAttribute("projectAll", projectClient.readAll(securityCookie.getValue()).collectList().block());
            model.addAttribute("buildingDTO", new BuildingCreateDTO());
            return "buildings";
        }
        catch (RuntimeException e){
            return "redirect:/login";
        }
    }

    @PostMapping("/buildings") /// post mapping to send project data to the sever
    public String addBuildingSubmit(Model model, @ModelAttribute BuildingCreateDTO buildingDTO, HttpServletRequest request) {
        try{
            var securityCookie = AuthWebController.getSecurityCookie(request);
            buildingClient.create(buildingDTO, securityCookie.getValue()).block();
            // model.addAttribute("buildingDTO", new BuildingCreateDTO());
            return "redirect:/buildings";
        }
        catch (RuntimeException e){
            return "redirect:/login";
        }
    }

    @PostMapping("/buildings/delete")
    public String deleteBuilding(@RequestParam Long buildingId, HttpServletRequest request) {
        try {
            var securityCookie = AuthWebController.getSecurityCookie(request);
            buildingClient.delete(buildingId, securityCookie.getValue()).block();
            return "redirect:/buildings";
        }
        catch (RuntimeException e){
                return "redirect:/login";
            }
    }
    @GetMapping("/buildings/edit") ///
    public String editFormBuildingClient(Model model, @RequestParam Long buildingId, HttpServletRequest request) //@RequestParam will take param from URL
    {
        try {
            var securityCookie = AuthWebController.getSecurityCookie(request);
            model.addAttribute("projectAll", projectClient.readAll(securityCookie.getValue()).collectList().block());
            model.addAttribute("buildingDTO", buildingClient.readById(buildingId, securityCookie.getValue()).block());
            return "buildingsEdit";
        }
        catch (RuntimeException e){
            return "redirect:/login";
        }
    }

    @PostMapping("/buildings/edit")
    public String editProjectClient(@ModelAttribute BuildingReadDTO buildingDTO, HttpServletRequest request) {

        try {
            var securityCookie = AuthWebController.getSecurityCookie(request);
            buildingClient.update(buildingDTO.convertToCreateDTO(), buildingDTO.getId(), securityCookie.getValue()).block();
            return "redirect:/buildings";
        }
        catch (RuntimeException e){
            return "redirect:/login";
        }
    }
}
