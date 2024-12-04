package seetransactions.use_case;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class SeeTransactionsInteractorTest {

    private SeeTransactionsDataAccessInterface seeTransactionsDataAccessInterface;
    private SeeTransactionsOutputBoundary seeTransactionsOutputBoundary;
    private SeeTransactionsInteractor seeTransactionsInteractor;

    @BeforeEach
    void setUp() {
        seeTransactionsDataAccessInterface = mock(SeeTransactionsDataAccessInterface.class);
        seeTransactionsOutputBoundary = mock(SeeTransactionsOutputBoundary.class);
        seeTransactionsInteractor = new SeeTransactionsInteractor(seeTransactionsDataAccessInterface, seeTransactionsOutputBoundary);
    }

    @Test
    void testSwitchToLoggedinView() {
        // When
        seeTransactionsInteractor.switchToLoggedinView();

        // Then
        verify(seeTransactionsOutputBoundary).switchToLoggedinView();  // Verify that the switch to logged-in view was invoked
    }
}
