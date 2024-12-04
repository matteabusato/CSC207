package login.interface_adapter;

import login.use_case.LoginInputBoundary;
import login.use_case.LoginInputData;

/**
 * The controller for the Login Use Case.
 */
public class LoginController {

    private final LoginInputBoundary loginUseCaseInteractor;

    public LoginController(LoginInputBoundary loginUseCaseInteractor) {
        this.loginUseCaseInteractor = loginUseCaseInteractor;
    }

    /**
     * Executes the Login Use Case.
     * @param userId the username of the user logging in
     * @param password the password of the user logging in
     */
    public void execute(int userId, String password) {
        final LoginInputData loginInputData = new LoginInputData(
                userId, password);

        loginUseCaseInteractor.execute(loginInputData);
    }

    /**
     * Executes the "switch to WelcomeView" Use Case.
     */
    public void switchToWelcomeView() {
        loginUseCaseInteractor.switchToWelcomeView();
    }
}
