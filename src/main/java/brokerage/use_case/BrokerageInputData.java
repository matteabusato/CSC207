package brokerage.use_case;

import entity.Stock;
import entity.User;

public class BrokerageInputData {
    private final User user;
    private final String stockSymbol;
    private final int quantity;
    private final double price;

    public BrokerageInputData(User user, String stockSymbol, int quantity, double price) {
        this.user = user;
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.price = price;
    }

    public BrokerageInputData(String stockSymbol) {
        this.user = new User();
        this.stockSymbol = stockSymbol;
        this.quantity = 0;
        this.price = 0.0;
    }

    public User getUser() {
        return user;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}
