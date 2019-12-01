package demo;

import demo.requests.CompleteRequest;
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

    @GetMapping("/login")
    public String login() {
        return "login";
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
