package fit.lunevale.client.ui;

import fit.lunevale.client.client.AuthClient;
import fit.lunevale.client.client.EngineerClient;
import fit.lunevale.client.model.client.EngineerRegistrationDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
public class EngineerWebController {

    private final EngineerClient engineerClient;
    private final AuthClient authClient;

    public EngineerWebController(EngineerClient engineerClient, AuthClient authClient) {
        this.engineerClient = engineerClient;
        this.authClient = authClient;
    }


    @GetMapping("/home")
    public String addClientHome(Model model, HttpServletRequest request){
        try {
            var securityCookie = AuthWebController.getSecurityCookie(request);
            var id = authClient.getUserId(securityCookie.getValue());
            model.addAttribute("currentEngineerDTO",
                    engineerClient.readById(id.block(), securityCookie.getValue()).block());
            return "home";
        }
        catch (RuntimeException e){
            return "redirect:/login";
        }
    }

    @GetMapping("/profile") ///
    public String getProfileClient(Model model, HttpServletRequest request) {
        try {
            var securityCookie = AuthWebController.getSecurityCookie(request);
            var id = authClient.getUserId(securityCookie.getValue()).block();
            return "profile";
        }
        catch (RuntimeException e) {
            return "redirect:/login";
        }

    }

    @PostMapping("/profile") ///
    public String deleteProfileEngineer(Model model, HttpServletRequest request, HttpServletResponse response) {

        try {
            var securityCookie = AuthWebController.getSecurityCookie(request);
            var id = authClient.getUserId(securityCookie.getValue());
            engineerClient.delete(id.block(), securityCookie.getValue()).block();
            Cookie cookie = AuthWebController.getSecurityCookie(request);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return "redirect:/login";
        }
        catch (RuntimeException e) {
            return "redirect:/login";
        }
    }

    @GetMapping("/profile/edit") ///
    public String editFormProfileClient(Model model, HttpServletRequest request) {
        try {
            var securityCookie = AuthWebController.getSecurityCookie(request);
            var id = authClient.getUserId(securityCookie.getValue());
            var currentEngineer = engineerClient.readById(id.block(), securityCookie.getValue()).block();

            EngineerRegistrationDTO currentData = new EngineerRegistrationDTO();
            if (currentEngineer != null) {
                currentData.setName(currentEngineer.getName());
                currentData.setSurname(currentEngineer.getSurname());
                currentData.setEmail(currentEngineer.getEmail());
                currentData.setPassword(currentEngineer.getPassword());
                model.addAttribute("engineerRegistrationDTO", currentData);
            }
            return "edit";
        }
        catch (RuntimeException e) {
            return "redirect:/login";
        }
    }

    @PostMapping("/profile/edit")
    public String editProfileClient(Model model, @ModelAttribute EngineerRegistrationDTO engineerRegistrationDTO, HttpServletRequest request) {

       try {
           var securityCookie = AuthWebController.getSecurityCookie(request);
           /// if the password and password verification are not the same throws error in the http
           if (!Objects.equals(engineerRegistrationDTO.getPassword(), engineerRegistrationDTO.getPasswordRepeated())) {
               model.addAttribute("engineerRegistrationSubmit", "Passwords don't match");
               model.addAttribute("engineerRegistrationDTO", engineerRegistrationDTO);
               return "edit";
           }

           if (engineerRegistrationDTO.getPassword().length() < 4) {
               model.addAttribute("engineerRegistrationSubmit", "Password should be at least 8 characters");
               model.addAttribute("engineerRegistrationDTO", engineerRegistrationDTO);
               return "edit";
           }
           var id = authClient.getUserId(AuthWebController.getSecurityCookie(request).getValue());
           model.addAttribute("engineerRegistrationSubmit",
                   engineerClient.update(engineerRegistrationDTO.convertToCreateDTO(), id.block(), securityCookie.getValue()).block());
           System.out.println(engineerRegistrationDTO.getName());

           return "edit";
       }
       catch (RuntimeException e) {
           return "redirect:/login";
       }
    }

    @GetMapping("/people")
    public String listAllPeople (Model model, HttpServletRequest request) {

       try {
           var securityCookie = AuthWebController.getSecurityCookie(request);
           model.addAttribute("engineerAll", engineerClient.readAll(securityCookie.getValue()).collectList().block());
           return "people";
       }
       catch (RuntimeException e) {
           return "redirect:/login";
       }
    }

    @GetMapping("/engineer/working") ///
    public String workersOnProject(Model model, @RequestParam Long projectId, HttpServletRequest request) //@RequestParam will take param from URL
    {
        try {
            var securityCookie = AuthWebController.getSecurityCookie(request);
            model.addAttribute("engineerAll",
                    engineerClient.getAllClientsByProjectId(projectId, securityCookie.getValue()).collectList().block());
            return "workers";
        }
        catch (RuntimeException e) {
            return "redirect:/login";
        }
    }

}
