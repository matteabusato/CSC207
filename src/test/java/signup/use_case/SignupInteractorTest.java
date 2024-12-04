package signup.use_case;

import entity.User;
import entity.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class SignupInteractorTest {

    private SignupUserDataAccessInterface userDataAccessObject;
    private SignupOutputBoundary signupOutputBoundary;
    private UserFactory userFactory;
    private SignupInteractor signupInteractor;

    @BeforeEach
    void setUp() {
        userDataAccessObject = mock(SignupUserDataAccessInterface.class);
        signupOutputBoundary = mock(SignupOutputBoundary.class);
        userFactory = mock(UserFactory.class);

        signupInteractor = new SignupInteractor(userDataAccessObject, signupOutputBoundary, userFactory);
    }

    @Test
    void testExecuteSuccessfulSignup() {
        // Given
        String name = "John";
        String surname = "Doe";
        String password = "password123";
        SignupInputData signupInputData = new SignupInputData(name, surname, password);

        // Mock the UserFactory to return a mock user
        User mockUser = mock(User.class);
        when(userFactory.create(name, surname, password)).thenReturn(mockUser);

        // When
        signupInteractor.execute(signupInputData);

        // Then
        verify(userDataAccessObject).save(mockUser);  // Verify that the user was saved
        verify(signupOutputBoundary).prepareSuccessView(any(SignupOutputData.class));  // Verify that the success view was prepared
    }

    @Test
    void testSwitchToWelcomeView() {
        // When
        signupInteractor.switchToWelcomeView();

        // Then
        verify(signupOutputBoundary).switchToWelcomeView();  // Verify that the welcome view is switched to
    }
}