package brokerage.interface_adapter;

import brokerage.use_case.BrokerageOutputBoundary;
import brokerage.use_case.BrokerageOutputData;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import interface_adapter.ViewManagerModel;
import loggedin.interface_adapter.LoggedinState;
import loggedin.interface_adapter.LoggedinViewModel;

import java.util.List;

/**
 * Handles the presentation layer for the brokerage system, managing the interaction
 * between the controller and the view.
 */

public class BrokeragePresenter implements BrokerageOutputBoundary {
    private final BrokerageViewModel brokerageViewModel;
    private final LoggedinViewModel loggedinViewModel;
    private final ViewManagerModel viewManagerModel;

    public BrokeragePresenter(BrokerageViewModel brokerageViewModel, LoggedinViewModel loggedinViewModel,
                              ViewManagerModel viewManagerModel) {
        this.brokerageViewModel = brokerageViewModel;
        this.loggedinViewModel = loggedinViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareTradeView(BrokerageOutputData outputData) {
        final double price = outputData.getPrice();
        final List<StockUnit> stocks = outputData.getStocks();
        final BrokerageState brokerageState = brokerageViewModel.getState();
        brokerageState.setPrice(price);
        brokerageState.setStocks(stocks);
        this.brokerageViewModel.setState(brokerageState);
        this.brokerageViewModel.firePropertyChanged();
    }

    @Override
    public void prepareSuccessView(BrokerageOutputData outputData) {
        final LoggedinState loggedInState = loggedinViewModel.getState();
        loggedInState.setUser(outputData.getUser());
        this.loggedinViewModel.setState(loggedInState);
        this.loggedinViewModel.firePropertyChanged();

        viewManagerModel.setState(loggedinViewModel.getViewName());
        viewManagerModel.firePropertyChanged();

    }

    @Override
    public void prepareFailView(String errorMessage) {
        final BrokerageState brokerageState = brokerageViewModel.getState();
        brokerageViewModel.firePropertyChanged();
    }

    @Override
    public void switchToLoggedinView() {
        viewManagerModel.setState(loggedinViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
