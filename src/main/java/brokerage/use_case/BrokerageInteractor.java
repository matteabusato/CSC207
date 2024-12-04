package brokerage.use_case;

import brokerage.api.StockApi;
import entity.Stock;
import entity.StockFactory;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import entity.User;
import java.util.ArrayList;
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
        final User user = input.getUser();
        final String stockSymbol = input.getStockSymbol();
        if (stockSymbol.isEmpty()) {
            brokerageOutputBoundary.prepareFailView("Insert Stock Symbol");
        }
        else {
            // final List<StockUnit> stocks = stockApi.execute(stockSymbol);
            //final BrokerageOutputData brokerageOutputData = new BrokerageOutputData(user, stockSymbol, 0,
            //        stocks.get(0).getClose(), stocks);
            final BrokerageOutputData brokerageOutputData = new BrokerageOutputData(user, stockSymbol, 0,
                    10, new ArrayList<StockUnit>());
            brokerageOutputBoundary.prepareTradeView(brokerageOutputData);
        }
    }

    @Override
    public void tradeStock(BrokerageInputData input) {
        final User user = input.getUser();
        final String stockSymbol = input.getStockSymbol();
        final int quantity = input.getQuantity();
        final double price = input.getPrice();

        final int quantityOwned = brokerageDataAccessInterface.getQuantity(user.getUserID(), stockSymbol);
        if (-1 * quantity > quantityOwned) {
            brokerageOutputBoundary.prepareFailView("You don't have enough shares. You currently have " + quantityOwned + " shares of " + stockSymbol + ". ");
        }
        else {
            Stock stock = stockFactory.create(stockSymbol, quantity, price);
            brokerageDataAccessInterface.saveData(user.getUserID(), stock);
            final BrokerageOutputData brokerageOutputData = new BrokerageOutputData(user, stockSymbol, quantity, price,
                    new ArrayList<StockUnit>());
            brokerageOutputBoundary.prepareSuccessView(brokerageOutputData);
        }
    }

    @Override
    public void switchToLoggedinView() {
        brokerageOutputBoundary.switchToLoggedinView();
    }
}

