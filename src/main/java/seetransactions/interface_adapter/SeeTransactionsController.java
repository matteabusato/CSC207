package seetransactions.interface_adapter;

import entity.User;
import seetransactions.use_case.SeeTransactionsInputBoundary;

/**
 * The controller for the See Transactions Use Case.
 */
public class SeeTransactionsController {
    private final SeeTransactionsInputBoundary seeTransactionInteractor;

    public SeeTransactionsController(SeeTransactionsInputBoundary seeTransactionInteractor) {
        this.seeTransactionInteractor = seeTransactionInteractor;
    }
    /**
     * Executes the "switch to WelcomeView" Use Case.
     */
    public void switchToLoggedinView() {
        seeTransactionInteractor.switchToLoggedinView();
    }
}
