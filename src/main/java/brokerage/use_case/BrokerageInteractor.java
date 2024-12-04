package brokerage.use_case;

import brokerage.api.StockApi;
import com.crazzyghost.alphavantage.timeseries.TimeSeries;
import entity.Stock;
import entity.StockFactory;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import entity.User;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class BrokerageInteractor implements BrokerageInputBoundary {

    private BrokerageDataAccessInterface brokerageDataAccessInterface;
    private BrokerageOutputBoundary brokerageOutputBoundary;
    private StockFactory stockFactory;
    private StockApi stockApi = new StockApi();

    public BrokerageInteractor(BrokerageDataAccessInterface brokerageDataAccessInterface,
                               BrokerageOutputBoundary brokerageOutputBoundary, StockFactory stockFactory) {
        this.brokerageDataAccessInterface = brokerageDataAccessInterface;
        this.brokerageOutputBoundary = brokerageOutputBoundary;
        this.stockFactory = stockFactory;
    }

    @Override
    public void searchStock(BrokerageInputData input) {
        final String stockSymbol = input.getStockSymbol();
        if (stockSymbol.isEmpty()) {
            brokerageOutputBoundary.prepareFailView("Insert Stock Symbol");
        }
        else {
            final List<StockUnit> stocks = stockApi.execute(stockSymbol);
            final BrokerageOutputData brokerageOutputData = new BrokerageOutputData(stockSymbol, stocks.get(0).getClose(), stocks);
            brokerageOutputBoundary.prepareTradeView(brokerageOutputData);
        }
    }

    @Override
    public void tradeStock(BrokerageInputData input) {
        final User user = input.getUser();
        final String stockSymbol = input.getStockSymbol();
        final int quantity = input.getQuantity();
        final Stock stock = input.getStock();

        final int quantityOwned = brokerageDataAccessInterface.getQuantity(user.getUserID(), stockSymbol);
        if (-1 * quantity > quantityOwned) {
            brokerageOutputBoundary.prepareFailView("Not enough stock");
        }
        else {
            brokerageDataAccessInterface.saveData(user.getUserID(), stock);
            final BrokerageOutputData brokerageOutputData = new BrokerageOutputData(user, stockSymbol, quantity);
            brokerageOutputBoundary.prepareSuccessView(brokerageOutputData);
        }
    }

    @Override
    public void switchToLoggedinView() {
        brokerageOutputBoundary.switchToLoggedinView();
    }
}

