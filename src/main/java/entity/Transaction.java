package entity;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a transaction object in the system.
 */
public class Transaction {
    private String transactionID;
    private int senderID;
    private int receiverID;
    private String cardUsed;
    private double amount;
    private LocalDateTime timeStamp;

    public Transaction() {
    }

    public Transaction(String transactionID, int senderID, int receiverID,
                             String cardUsed, double amount, LocalDateTime timeStamp) {
        this.transactionID = transactionID;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.cardUsed = cardUsed;
        this.amount = amount;
        this.timeStamp = timeStamp;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public int getSenderID() {
        return senderID;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public String getCardUsed() {
        return cardUsed;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public void setCardUsed(String cardUsed) {
        this.cardUsed = cardUsed;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return senderID == that.senderID &&
                receiverID == that.receiverID &&
                Double.compare(that.amount, amount) == 0 &&
                Objects.equals(transactionID, that.transactionID) &&
                Objects.equals(cardUsed, that.cardUsed) &&
                Objects.equals(timeStamp, that.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionID, senderID, receiverID, cardUsed, amount, timeStamp);
    }

}
