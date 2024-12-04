package signup.interface_adapter;

import signup.use_case.SignupInputBoundary;
import signup.use_case.SignupInputData;

/**
 * Controller for the Signup Use Case.
 */
public class SignupController {

    private final SignupInputBoundary signupUseCaseInteractor;

    public SignupController(SignupInputBoundary signupUseCaseInteractor) {
        this.signupUseCaseInteractor = signupUseCaseInteractor;
    }

    /**
     * Executes the Signup Use Case.
     * @param name the username to sign up
     * @param surname the password
     * @param password the password repeated
     */
    public void execute(String name, String surname, String password) {
        final SignupInputData signupInputData = new SignupInputData(
                name, surname, password);

        signupUseCaseInteractor.execute(signupInputData);
    }

    /**
     * Executes the "switch to WelcomeView" Use Case.
     */
    public void switchToWelcomeView() {
        signupUseCaseInteractor.switchToWelcomeView();
    }
}
