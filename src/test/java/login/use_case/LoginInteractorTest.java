package login.use_case;

import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class LoginInteractorTest {

    private LoginUserDataAccessInterface userDataAccessObject;
    private LoginOutputBoundary loginOutputBoundary;
    private LoginInteractor loginInteractor;

    @BeforeEach
    void setUp() {
        // Mock the dependencies
        userDataAccessObject = mock(LoginUserDataAccessInterface.class);
        loginOutputBoundary = mock(LoginOutputBoundary.class);

        // Create the LoginInteractor with mocked dependencies
        loginInteractor = new LoginInteractor(userDataAccessObject, loginOutputBoundary);
    }

    @Test
    void testExecuteSuccessfulLogin() {
        // Given
        int userId = 1;
        String password = "password123";
        LoginInputData inputData = new LoginInputData(userId, password);

        // Mock the behavior of the userDataAccessObject
        when(userDataAccessObject.correspondsToUser(userId, password)).thenReturn(true);

        User mockUser = mock(User.class);
        when(userDataAccessObject.get(userId)).thenReturn(mockUser);

        LoginOutputData mockOutputData = new LoginOutputData(mockUser);

        // When
        loginInteractor.execute(inputData);

        // Then
        verify(loginOutputBoundary).prepareSuccessView(mockOutputData);
        verify(loginOutputBoundary, never()).prepareFailView(anyString());
    }

    @Test
    void testExecuteFailedLogin() {
        // Given
        int userId = 1;
        String password = "wrongPassword";
        LoginInputData inputData = new LoginInputData(userId, password);

        // Mock the behavior of the userDataAccessObject
        when(userDataAccessObject.correspondsToUser(userId, password)).thenReturn(false);

        // When
        loginInteractor.execute(inputData);

        // Then
        verify(loginOutputBoundary).prepareFailView(" Incorrect userId or Password. ");
        verify(loginOutputBoundary, never()).prepareSuccessView(any());
    }

    @Test
    void testSwitchToWelcomeView() {
        // When
        loginInteractor.switchToWelcomeView();

        // Then
        verify(loginOutputBoundary).switchToWelcomeView();
    }
}
