package brokerage.use_case;

import java.util.List;

import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import entity.User;

/**
 *  Output data for the brokerage use case.
 */
public class BrokerageOutputData {
    private final User user;
    private final String stockSymbol;
    private final int quantity;
    private final double price;
    private final List<StockUnit> stocks;

    public BrokerageOutputData(User user, String stockSymbol, int quantity, double price, List<StockUnit> stocks) {
        this.user = user;
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.price = price;
        this.stocks = stocks;
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

    public List<StockUnit> getStocks() {
        return stocks;
    }
}
