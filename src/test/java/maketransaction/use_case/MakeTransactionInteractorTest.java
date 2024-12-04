package maketransaction.use_case;

import entity.Transaction;
import entity.TransactionFactory;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class MakeTransactionInteractorTest {

    private MakeTransactionDataAccessInterface makeTransactionDataAccessInterface;
    private MakeTransactionOutputBoundary makeTransactionOutputBoundary;
    private TransactionFactory transactionFactory;
    private MakeTransactionInteractor makeTransactionInteractor;

    private User mockUser;
    private User mockReceiver;

    @BeforeEach
    void setUp() {
        makeTransactionDataAccessInterface = mock(MakeTransactionDataAccessInterface.class);
        makeTransactionOutputBoundary = mock(MakeTransactionOutputBoundary.class);
        transactionFactory = mock(TransactionFactory.class);

        makeTransactionInteractor = new MakeTransactionInteractor(makeTransactionDataAccessInterface, makeTransactionOutputBoundary, transactionFactory);

        mockUser = mock(User.class);
        mockReceiver = mock(User.class);
    }

    @Test
    void testExecuteSuccessfulTransaction() {
        // Given
        int receiverId = 123;
        String card = "123456789";
        double amount = 50.0;

        // Set up mock user
        when(mockUser.getBalance()).thenReturn(100.0);
        when(mockUser.getUserID()).thenReturn(1);

        // Set up receiver
        when(makeTransactionDataAccessInterface.getUser(receiverId)).thenReturn(mockReceiver);
        when(mockReceiver.getUserID()).thenReturn(2);

        // Set up transaction factory to return a mock transaction
        Transaction mockTransaction = mock(Transaction.class);
        when(transactionFactory.create(1, 2, card, amount)).thenReturn(mockTransaction);

        // When
        MakeTransactionInputData makeTransactionInputData = new MakeTransactionInputData(mockUser, receiverId, card, amount);
        makeTransactionInteractor.execute(makeTransactionInputData);

        // Then
        verify(makeTransactionDataAccessInterface).getUser(receiverId);  // Verify that receiver data was fetched
        verify(makeTransactionDataAccessInterface).save(mockTransaction);  // Verify that the transaction was saved
        verify(makeTransactionOutputBoundary).prepareSuccessView(any(MakeTransactionOutputData.class));  // Verify that the success view was prepared
    }

    @Test
    void testExecuteFailedTransaction_NotEnoughBalance() {
        // Given
        int receiverId = 123;
        String card = "123456789";
        double amount = 200.0;  // Amount exceeds balance

        // Set up mock user with insufficient balance
        when(mockUser.getBalance()).thenReturn(100.0);
        when(mockUser.getUserID()).thenReturn(1);

        // Set up receiver
        when(makeTransactionDataAccessInterface.getUser(receiverId)).thenReturn(mockReceiver);
        when(mockReceiver.getUserID()).thenReturn(2);

        // When
        MakeTransactionInputData makeTransactionInputData = new MakeTransactionInputData(mockUser, receiverId, card, amount);
        makeTransactionInteractor.execute(makeTransactionInputData);

        // Then
        verify(makeTransactionDataAccessInterface, never()).save(any(Transaction.class));  // Verify that no transaction was saved
        verify(makeTransactionOutputBoundary).prepareFailView(" Not enough balance. ");  // Verify that the failure view was prepared
    }

    @Test
    void testSwitchToLoggedinView() {
        // When
        makeTransactionInteractor.switchToLoggedinView();

        // Then
        verify(makeTransactionOutputBoundary).switchToLoggedinView();  // Verify that the logged-in view was switched
    }
}
