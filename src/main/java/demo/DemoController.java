package demo;

import demo.requests.CompleteRequest;
import demo.requests.SignupRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DemoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

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

//    @GetMapping("/login")
//    public String login(Model model) {
//        model.addAttribute("login_request", new LoginRequest());
//        return "login";
//    }
//
//    @PostMapping("/login")
//    public String loginSubmit(@ModelAttribute LoginRequest loginRequest) {
//        LOGGER.debug("Received login for {} with password {}", loginRequest.getEmail(), loginRequest.getPassword());
//        return "user_home";
//    }

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
