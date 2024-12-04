package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransactionFactoryTest {

    private TransactionFactory transactionFactory;

    @BeforeEach
    void setUp() {
        transactionFactory = new TransactionFactory();
    }

    @Test
    void testGenerateTransactionId() {
        // Given
        int senderID = 123;
        int receiverID = 456;
        LocalDateTime timeStamp = LocalDateTime.of(2024, 12, 4, 10, 30, 45, 0);

        // When
        String transactionId = transactionFactory.generateTransactionId(senderID, receiverID, timeStamp);

        // Then
        String expectedTransactionId = "1234562024-12-04T10:30:45";
        assertEquals(expectedTransactionId, transactionId, "Transaction ID should match the expected format.");
    }

    @Test
    void testCreateTransaction() {
        // Given
        int senderID = 123;
        int receiverID = 456;
        String cardUsed = "Visa";
        double amount = 100.0;

        // When
        Transaction transaction = transactionFactory.create(senderID, receiverID, cardUsed, amount);

        // Then
        assertNotNull(transaction, "Transaction should not be null.");
        assertEquals(senderID, transaction.getSenderID(), "Sender ID should match.");
        assertEquals(receiverID, transaction.getReceiverID(), "Receiver ID should match.");
        assertEquals(cardUsed, transaction.getCardUsed(), "Card used should match.");
        assertEquals(amount, transaction.getAmount(), "Amount should match.");
        assertNotNull(transaction.getTimeStamp(), "Timestamp should not be null.");
        assertTrue(transaction.getTransactionID().startsWith("123456"), "Transaction ID should start with the sender and receiver IDs.");
    }
}
