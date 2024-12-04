package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {
    private Transaction transaction;
    private final String transactionID = "TX12345";
    private final int senderID = 123;
    private final int receiverID = 456;
    private final String cardUsed = "Visa";
    private final double amount = 50.0;
    private final LocalDateTime timeStamp = LocalDateTime.of(2024, 12, 1, 10, 0);

    @BeforeEach
    void setUp() {
        transaction = new Transaction(transactionID, senderID, receiverID, cardUsed, amount, timeStamp);
    }

    @Test
    void testConstructor() {
        assertEquals(transactionID, transaction.getTransactionID(), "Transaction ID should match the constructor input.");
        assertEquals(senderID, transaction.getSenderID(), "Sender ID should match the constructor input.");
        assertEquals(receiverID, transaction.getReceiverID(), "Receiver ID should match the constructor input.");
        assertEquals(cardUsed, transaction.getCardUsed(), "Card used should match the constructor input.");
        assertEquals(amount, transaction.getAmount(), "Amount should match the constructor input.");
        assertEquals(timeStamp, transaction.getTimeStamp(), "Timestamp should match the constructor input.");
    }

    @Test
    void testSetAmount() {
        double newAmount = 75.5;
        transaction.setAmount(newAmount);
        assertEquals(newAmount, transaction.getAmount(), "Amount should be updated to the new value.");
    }

    @Test
    void testSetCardUsed() {
        String newCardUsed = "MasterCard";
        transaction.setCardUsed(newCardUsed);
        assertEquals(newCardUsed, transaction.getCardUsed(), "Card used should be updated to the new value.");
    }

    @Test
    void testEqualsAndHashCode() {
        Transaction anotherTransaction = new Transaction(transactionID, senderID, receiverID, cardUsed, amount, timeStamp);
        assertEquals(transaction, anotherTransaction, "Transactions with identical properties should be equal.");
        assertEquals(transaction.hashCode(), anotherTransaction.hashCode(),
                "Hash codes of equal transactions should be identical.");
    }
}
