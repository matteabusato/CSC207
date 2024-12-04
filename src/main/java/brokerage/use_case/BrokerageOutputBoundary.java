package brokerage.use_case;

/**
 * The output boundary for brokerage-related use cases.
 * This interface defines methods to handle and prepare views
 * for different outcomes in the brokerage system.
 */
public interface BrokerageOutputBoundary {

    /**
     * Prepares the view for displaying trade details or options.
     *
     * @param outputData the output data containing trade-related information
     */
    void prepareTradeView(BrokerageOutputData outputData);

    /**
     * Prepares the view for displaying a successful operation result.
     *
     * @param outputData the output data containing details of the successful operation
     */
    void prepareSuccessView(BrokerageOutputData outputData);

    /**
     * Prepares the view for displaying a failure or error message.
     *
     * @param errorMessage a description of the error or failure reason
     */
    void prepareFailView(String errorMessage);

    /**
     * Switches the application interface to the view intended for logged-in users.
     * Invoked to redirect the user after successful authentication or other actions.
     */
    void switchToLoggedinView();
}
