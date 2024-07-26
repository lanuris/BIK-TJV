package fit.lunevale.client.ui;

import fit.lunevale.client.client.AuthClient;
import fit.lunevale.client.model.client.EngineerDTO;
import fit.lunevale.client.model.client.EngineerLoginDTO;
import fit.lunevale.client.model.client.EngineerRegistrationDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Controller
public class AuthWebController {

    private final AuthClient authClient;
    private static String tokenKey;


    public AuthWebController(AuthClient authClient, @Value("${token_key}") String tokenKey) {
        this.authClient = authClient;
        AuthWebController.tokenKey = tokenKey;
    }

    @GetMapping("")
    public String emptyUrl(Model model, HttpServletRequest request) {
        try {
            var securityCookie = AuthWebController.getSecurityCookie(request);
            var id = authClient.getUserId(securityCookie.getValue()).block();
            return "home";
        }
        catch (RuntimeException e){
            return "redirect:/login";
        }
    }

    @GetMapping("/login")
    public String enterRenderClient(Model model, HttpServletRequest request) {
        model.addAttribute("engineerLoginDTO", new EngineerLoginDTO());
        return "login";
    }

    @PostMapping("/login")
    public String enterLoginClient(Model model, @ModelAttribute EngineerLoginDTO engineerLoginDTO, HttpServletResponse response) throws Exception {

        try {
            ResponseCookie responseCookie = authClient.login(engineerLoginDTO).block();
            if (responseCookie != null)
                response.addCookie(convertCookie(responseCookie));
        }
        catch (RuntimeException e){
            model.addAttribute("engineerLoginSubmit", e.getMessage());
            model.addAttribute("engineerLoginDTO", new EngineerLoginDTO());
            return "login";
        }

        return "redirect:/home";
    }

    @GetMapping("/logout") /// mapping to log out from the system
    public String clientLogout(HttpServletRequest request, HttpServletResponse response) {
        try {
            Cookie cookie = AuthWebController.getSecurityCookie(request);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return "redirect:/login";
        } catch (RuntimeException e){
            return "redirect:/login";
        }
    }


    @GetMapping("/registration") /// get mapping for the registration page
    public String addClientRender(Model model) {

        /// attribute that creates empty RegistrationModel
        model.addAttribute("engineerRegistrationDTO", new EngineerRegistrationDTO());
        return "register";
    }

    @PostMapping("/registration") /// post mapping to send the registering user credentials to the sever
    public String addClientSubmit(Model model,
                                  @ModelAttribute EngineerRegistrationDTO engineerRegistrationDTO,
                                  HttpServletResponse response) {
        /// if the password and password verification are not the same throws error in the http
        if (!Objects.equals(engineerRegistrationDTO.getPassword(), engineerRegistrationDTO.getPasswordRepeated())){
            model.addAttribute("engineerRegistrationSubmit", "Passwords don't match");
            model.addAttribute("engineerRegistrationDTO", new EngineerRegistrationDTO());
            return "register";
        }

        if (engineerRegistrationDTO.getPassword().length()<4)
        {
            model.addAttribute("engineerRegistrationSubmit", "Password should be at least 4 characters");
            model.addAttribute("engineerRegistrationDTO", new EngineerRegistrationDTO());
            return "register";
        }
        try {
            ResponseCookie responseCookie = authClient.register(engineerRegistrationDTO.convertToCreateDTO()).block();
            model.addAttribute("engineerRegistrationSubmit", responseCookie);
            if (responseCookie != null)
                response.addCookie(convertCookie(responseCookie));
        }catch (RuntimeException e){
            model.addAttribute("engineerRegistrationSubmit", e.getMessage());
        }
        model.addAttribute("engineerRegistrationDTO", new EngineerRegistrationDTO());
        return "register";
    }

    private Cookie convertCookie (ResponseCookie responseCookie){

        Cookie cookie = new Cookie(responseCookie.getName(), responseCookie.getValue());
        cookie.setHttpOnly(responseCookie.isHttpOnly());
        cookie.setSecure(responseCookie.isSecure()); // Ensure this is only over HTTPS in production
        cookie.setPath(responseCookie.getPath());
        cookie.setMaxAge((int)responseCookie.getMaxAge().getSeconds()); // 1 day
        return cookie;
    }

    public static Cookie getSecurityCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            Optional<Cookie> jwtCookie = Arrays.stream(request.getCookies())
                    .filter(cookie -> tokenKey.equals(cookie.getName()))
                    .findFirst();
            if (jwtCookie.isPresent()) {
                return jwtCookie.get();
            }
        }
        throw new RuntimeException("JWT cookie not found");
    }

}
