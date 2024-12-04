package login.use_case;

/**
 * Input Boundary for actions which are related to logging in.
 */
public interface LoginInputBoundary {

    /**
     * Executes the login use case.
     * @param loginInputData the input data
     */
    void execute(LoginInputData loginInputData);

    /**
     * Executes the welcome use case.
     */
    void switchToWelcomeView();
}
