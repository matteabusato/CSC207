package seetransactions.use_case;

import java.util.List;

import entity.Transaction;
import entity.User;

/**
 * View class for handling the see transactions interface.
 */
public class SeeTransactionsInteractor implements SeeTransactionsInputBoundary {
    private final SeeTransactionsDataAccessInterface seeTransactionsDataAccessInterface;
    private final SeeTransactionsOutputBoundary seeTransactionsOutputBoundary;

    public SeeTransactionsInteractor(SeeTransactionsDataAccessInterface seeTransactionsDataAccessInterface,
                                     SeeTransactionsOutputBoundary seeTransactionsOutputBoundary) {
        this.seeTransactionsDataAccessInterface = seeTransactionsDataAccessInterface;
        this.seeTransactionsOutputBoundary = seeTransactionsOutputBoundary;
    }

    @Override
    public void switchToLoggedinView() {
        seeTransactionsOutputBoundary.switchToLoggedinView();
    }
}
