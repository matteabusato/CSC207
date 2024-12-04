package seetransactions.interface_adapter;

import interface_adapter.ViewManagerModel;
import loggedin.interface_adapter.LoggedinViewModel;
import seetransactions.use_case.SeeTransactionsOutputBoundary;

/**
 * The Presenter for the Login Use Case.
 */
public class SeeTransactionsPresenter implements SeeTransactionsOutputBoundary {
    private final SeeTransactionsViewModel seeTransactionsViewModel;
    private final LoggedinViewModel loggedinViewModel;
    private final ViewManagerModel viewManagerModel;

    public SeeTransactionsPresenter(SeeTransactionsViewModel seeTransactionsViewModel,
                                    LoggedinViewModel loggedinViewModel, ViewManagerModel viewManagerModel) {
        this.seeTransactionsViewModel = seeTransactionsViewModel;
        this.loggedinViewModel = loggedinViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void switchToLoggedinView() {
        viewManagerModel.setState(loggedinViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
