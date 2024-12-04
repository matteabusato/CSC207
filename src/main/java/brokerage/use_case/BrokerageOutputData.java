package brokerage.use_case;

import java.util.List;

import com.crazzyghost.alphavantage.timeseries.response.StockUnit;

/**
 *  Output data for the brokerage use case.
 */
public class BrokerageOutputData {
    private final String stockSymbol;
    private final double price;
    private final List<StockUnit> stocks;

    public BrokerageOutputData(String stockSymbol, double price, List<StockUnit> stocks) {
        this.stockSymbol = stockSymbol;
        this.price = price;
        this.stocks = stocks;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public double getPrice() {
        return price;
    }

    public List<StockUnit> getStocks() {
        return stocks;
    }
}
