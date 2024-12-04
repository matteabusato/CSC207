package brokerage.interface_adapter;

import entity.Stock;
import brokerage.use_case.BrokerageInputBoundary;
import brokerage.use_case.BrokerageInputData;
import entity.User;

/**
 * Controls the brokerage functionality for the application, including handling stock searches,
 * buying and selling stocks, and managing user data.
 */
public class BrokerageController {
    private final BrokerageInputBoundary brokerageInteractor;

    public BrokerageController(BrokerageInputBoundary brokerageInteractor) {
        this.brokerageInteractor = brokerageInteractor;
    }

    /**
     * Action for search.
     * @param stockSymbol stock
     */
    public void searchStock(String stockSymbol) {
        final BrokerageInputData brokerageInputData = new BrokerageInputData(stockSymbol);
        brokerageInteractor.searchStock(brokerageInputData);
    }

    /**
     * Action for trade.
     * @param user user
     * @param stockSymbol symbol
     * @param quantity quantity
     */
    public void tradeStock(User user, String stockSymbol, int quantity) {
        final BrokerageInputData brokerageInputData = new BrokerageInputData(user, stockSymbol, quantity);
        brokerageInteractor.tradeStock(brokerageInputData);
    }

    /**
     * Switches to LoggedinView.
     */
    public void switchToLoggedinView() {
        brokerageInteractor.switchToLoggedinView();
    }

}
