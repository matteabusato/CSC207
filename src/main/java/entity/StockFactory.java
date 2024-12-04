package entity;

public class StockFactory {
    /**
     * Creates a new Transaction.
     * @param stockID is a parameter
     * @param price is a parameter
     * @param quantity is a parameter
     * @return the new stock
     */
    public Stock create(String stockID, int quantity, double price) {

        return new Stock(stockID, quantity, price);
    }

}
