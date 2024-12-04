package brokerage.interface_adapter;

import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import entity.User;

import java.util.List;

public class BrokerageState {
    private User user;
    private String stockSymbol;
    private double price;
    private int quantity;
    private List<StockUnit> stocks;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<StockUnit> getStocks() {
        return stocks;
    }

    public void setStocks(List<StockUnit> stocks) {
        this.stocks = stocks;
    }
}
