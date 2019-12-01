package demo;

import demo.requests.CompleteRequest;
import demo.requests.LoginRequest;
import demo.requests.SignupRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DemoController {

    private final SignupService signupService;

    public DemoController(SignupService signupService) {
        this.signupService = signupService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public String home() {
        return "user_home";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("login_request", new LoginRequest());
        return "login";
    }

    @PostMapping("/login")
    public String loginSubmit(@ModelAttribute LoginRequest loginRequest) {
        System.out.println(String.format("Received login for %s with password %s", loginRequest.getEmail(), loginRequest.getPassword()));
        return "user_home";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("signup_request", new SignupRequest());
        return "signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(@ModelAttribute SignupRequest signupRequest) {
        signupService.doSignup(signupRequest.getEmail());
        return "signup_successful";
    }

    @GetMapping("/complete")
    public String complete(@RequestParam String email, Model model) {
        CompleteRequest completeRequest = new CompleteRequest();
        completeRequest.setEmail(email);
        model.addAttribute("complete_request", completeRequest);
        return "complete";
    }

    @PostMapping("/complete")
    public String completeSubmit(@ModelAttribute CompleteRequest completeRequest) {
        return "complete_successful";
    }
}
