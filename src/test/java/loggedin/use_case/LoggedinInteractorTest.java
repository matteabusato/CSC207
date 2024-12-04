package loggedin.use_case;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entity.User;

import static org.mockito.Mockito.*;

class LoggedinInteractorTest {

    private LoggedinOutputBoundary mockOutputBoundary;
    private LoggedinInteractor loggedinInteractor;
    private User user;

    @BeforeEach
    void setUp() {
        // Create mock of LoggedinOutputBoundary
        mockOutputBoundary = mock(LoggedinOutputBoundary.class);
        loggedinInteractor = new LoggedinInteractor(mockOutputBoundary);

        // Create a User instance for testing
        user = new User("john", "doe", "password123");  // Modify this constructor based on the actual User class
    }

    @Test
    void testSwitchToMakeTransactionView() {
        // Call the method
        loggedinInteractor.switchToMakeTransactionView(user);

        // Verify that the method on the output boundary is called with the correct user
        verify(mockOutputBoundary).switchToMakeTransactionView(user);
    }

    @Test
    void testSwitchToSeeTransactionHistoryView() {
        // Call the method
        loggedinInteractor.switchToSeeTransactionHistoryView(user);

        // Verify that the method on the output boundary is called with the correct user
        verify(mockOutputBoundary).switchToSeeTransactionHistoryView(user);
    }

    @Test
    void testSwitchToWelcomeView() {
        // Call the method
        loggedinInteractor.switchToWelcomeView();

        // Verify that the method on the output boundary is called
        verify(mockOutputBoundary).switchToWelcomeView();
    }
}
