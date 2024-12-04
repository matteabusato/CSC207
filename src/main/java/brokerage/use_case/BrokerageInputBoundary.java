package brokerage.use_case;

/**
 * The input boundary for brokerage-related use cases.
 * This interface defines methods for searching stocks, trading stocks,
 * and switching views for logged-in users.
 */
public interface BrokerageInputBoundary {
    /**
     * Searches for a stock based on the input data provided.
     *
     * @param input the input data containing search criteria for the stock
     */
    void searchStock(BrokerageInputData input);
    /**
     * Executes a trade operation (buy/sell) based on the input data provided.
     *
     * @param input the input data containing trade details
     */
    void tradeStock(BrokerageInputData input);

    /**
     * Switches the application interface to the view intended for logged-in users.
     * This is typically invoked after user authentication.
     */
    void switchToLoggedinView();
}
