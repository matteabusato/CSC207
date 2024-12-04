package maketransaction.interface_adapter;

import interface_adapter.ViewManagerModel;
import loggedin.interface_adapter.LoggedinState;
import loggedin.interface_adapter.LoggedinViewModel;
import maketransaction.use_case.MakeTransactionOutputBoundary;
import maketransaction.use_case.MakeTransactionOutputData;

/**
 * The Presenter for the Login Use Case.
 */
public class MakeTransactionPresenter implements MakeTransactionOutputBoundary {
    private final MakeTransactionViewModel makeTransactionViewModel;
    private final LoggedinViewModel loggedinViewModel;
    private final ViewManagerModel viewManagerModel;

    public MakeTransactionPresenter(MakeTransactionViewModel makeTransactionViewModel,
                                    LoggedinViewModel loggedinViewModel, ViewManagerModel viewManagerModel) {
        this.makeTransactionViewModel = makeTransactionViewModel;
        this.loggedinViewModel = loggedinViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(MakeTransactionOutputData response) {
        // On success, switch to the logged in view.

        final LoggedinState loggedInState = loggedinViewModel.getState();
        loggedInState.setUser(response.getUser());
        this.loggedinViewModel.setState(loggedInState);
        this.loggedinViewModel.firePropertyChanged();

        viewManagerModel.setState(loggedinViewModel.getViewName());
        viewManagerModel.firePropertyChanged();

    }

    @Override
    public void prepareFailView(String error) {
        final MakeTransactionState makeTransactionState = makeTransactionViewModel.getState();
        makeTransactionState.setTransactionError(error);
        makeTransactionViewModel.firePropertyChanged();
    }

    @Override
    public void switchToLoggedinView() {
        viewManagerModel.setState(loggedinViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
