package fit.lunevale.client.ui;

import fit.lunevale.client.client.AuthClient;
import fit.lunevale.client.client.ProjectClient;
import fit.lunevale.client.model.project.ProjectCreateDTO;
import fit.lunevale.client.model.project.ProjectDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Controller
public class ProjectWebController {

    private final ProjectClient projectClient;
    private final AuthClient authClient;

    public ProjectWebController(ProjectClient projectClient, AuthClient authClient) {
        this.projectClient = projectClient;
        this.authClient = authClient;
    }


    @GetMapping("/projects")
    public String listAllProjects (Model model, HttpServletRequest request) {
        try {
            var securityCookie = AuthWebController.getSecurityCookie(request);
            var id = authClient.getUserId(securityCookie.getValue());

            model.addAttribute("projectAll", projectClient.readAll(securityCookie.getValue()).collectList().block());
            model.addAttribute("engineerAll", projectClient.getAllOrderedProjectsId(id.block(), securityCookie.getValue()).collectList().block());
            model.addAttribute("projectDTO", new ProjectCreateDTO());
            return "projects";
        }
        catch (RuntimeException e){
            return "redirect:/login";
        }
    }

    @PostMapping("/projects") /// post mapping to send project data to the sever
    public String addProjectSubmit(Model model, @ModelAttribute ProjectCreateDTO projectDTO, HttpServletRequest request) {
        try {
            var securityCookie = AuthWebController.getSecurityCookie(request);
            projectClient.create(projectDTO, securityCookie.getValue()).block();
            return "redirect:/projects";
        } catch (RuntimeException e) {
            return "redirect:/login";
        }
    }

    @PostMapping("/projects/delete") ///
    public String deleteProfileClient(@RequestParam Long projectId, HttpServletRequest request) {
        try {
            var securityCookie = AuthWebController.getSecurityCookie(request);
            projectClient.delete(projectId, securityCookie.getValue()).block();
            return "redirect:/projects";
        } catch (RuntimeException e) {
            return "redirect:/login";
        }
    }

    @GetMapping("/projects/edit") ///
    public String editFormProjectClient(Model model, @RequestParam Long projectId, HttpServletRequest request) //@RequestParam will take param from URL
    {
        try {
            var securityCookie = AuthWebController.getSecurityCookie(request);
            model.addAttribute("projectDTO", projectClient.readById(projectId, securityCookie.getValue()).block());
            return "projectsEdit";
        } catch (RuntimeException e) {
            return "redirect:/login";
        }
    }

    @PostMapping("/projects/edit")
    public String editProjectClient(@ModelAttribute ProjectDTO projectDTO, HttpServletRequest request) {
        try {
            var securityCookie = AuthWebController.getSecurityCookie(request);
            projectClient.update(projectDTO.convertToCreateDTO(), projectDTO.getId(), securityCookie.getValue()).block();
            return "redirect:/projects";
        } catch (RuntimeException e) {
            return "redirect:/login";
        }
    }

    @PostMapping("/projects/update/working")
    public String signInToProject(@RequestParam Long projectId, HttpServletRequest request) {
        try{
            var id = authClient.getUserId(AuthWebController.getSecurityCookie(request).getValue());
            var securityCookie = AuthWebController.getSecurityCookie(request);
            projectClient.updateFreelancers(id.block(), projectId, securityCookie.getValue()).block();
            return "redirect:/projects";
        } catch (RuntimeException e) {
            return "redirect:/login";
        }
    }
}
