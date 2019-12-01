package demo;

import org.springframework.stereotype.Component;

@Component
public class SignupServiceImpl implements SignupService {
    @Override
    public void doSignup(String email) {
        System.out.println("Signing up with " + email);
    }
}
