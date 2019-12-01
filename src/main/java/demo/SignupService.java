package demo;

public interface SignupService {
    /**
     * Sign up a user with email <code>email</code>
     * @param email The email address of the user
     */
    void doSignup(String email);

    void completeSignup(String email, String password);
}
