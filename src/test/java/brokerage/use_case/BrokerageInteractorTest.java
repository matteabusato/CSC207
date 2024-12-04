package brokerage.use_case;

import brokerage.api.StockApi;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import entity.Stock;
import entity.StockFactory;
import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Mockito.*;

class BrokerageInteractorTest {

    private BrokerageDataAccessInterface mockDataAccess;
    private BrokerageOutputBoundary mockOutputBoundary;
    private StockFactory mockStockFactory;
    private BrokerageInteractor interactor;
    @Mock
    private StockApi mockStockApi;

    @BeforeEach
    void setUp() {
        mockDataAccess = mock(BrokerageDataAccessInterface.class);
        mockOutputBoundary = mock(BrokerageOutputBoundary.class);
        mockStockFactory = mock(StockFactory.class);
        mockStockApi = mock(StockApi.class);

        interactor = new BrokerageInteractor(mockDataAccess, mockOutputBoundary, mockStockFactory);
        interactor.stockApi = mockStockApi;
    }

    @Test
    void testSearchStockSuccess() {
        // Given
        User user = new User("John", "Doe", "password");
        String stockSymbol = "AAPL";

        // Mock the StockUnit
        StockUnit mockStockUnit = mock(StockUnit.class);
        when(mockStockUnit.getClose()).thenReturn(150.0);

        List<StockUnit> mockStockUnits = List.of(mockStockUnit);
        when(mockStockApi.execute(stockSymbol)).thenReturn(mockStockUnits);

        BrokerageInputData input = new BrokerageInputData(user, stockSymbol, 0, 0.0);

        // When
        interactor.searchStock(input);

        // Then
        verify(mockOutputBoundary).prepareTradeView(any(BrokerageOutputData.class));
        verifyNoInteractions(mockDataAccess, mockStockFactory);
    }


    @Test
    void testSearchStockWithEmptySymbol() {
        // Given
        User user = new User("John", "Doe", "password");
        BrokerageInputData input = new BrokerageInputData(user, "", 0, 0.0);

        // When
        interactor.searchStock(input);

        // Then
        verify(mockOutputBoundary).prepareFailView("Insert Stock Symbol");
        verifyNoInteractions(mockDataAccess, mockStockFactory, mockStockApi);
    }

    @Test
    public void testTradeStockSuccess() {
        // Mock dependencies
        BrokerageDataAccessInterface brokerageDataAccessInterface = mock(BrokerageDataAccessInterface.class);
        BrokerageOutputBoundary brokerageOutputBoundary = mock(BrokerageOutputBoundary.class);
        StockFactory stockFactory = mock(StockFactory.class);

        // Setup BrokerageInteractor
        BrokerageInteractor interactor = new BrokerageInteractor(
                brokerageDataAccessInterface,
                brokerageOutputBoundary,
                stockFactory
        );

        // Mock input data
        User user = new User("Joe", "Bob", "1000");
        user.setBalance(2000);
        BrokerageInputData input = new BrokerageInputData(user, "AAPL", 10, 150.0);

        // Mock behavior
        when(brokerageDataAccessInterface.getQuantity(10001, "AAPL")).thenReturn(10);
        when(stockFactory.create("AAPL", 10, 150.0)).thenReturn(new Stock("AAPL", 10, 150.0));
        when(brokerageDataAccessInterface.saveData(anyInt(), any(Stock.class))).thenReturn(user);

        // Execute the method
        interactor.tradeStock(input);

        // Verify saveData was called with the correct arguments
        verify(brokerageDataAccessInterface).saveData(eq(10001), any(Stock.class));

        // Verify success view was prepared
        verify(brokerageOutputBoundary).prepareSuccessView(any(BrokerageOutputData.class));
    }


    @Test
    void testTradeStockInsufficientShares() {
        // Given
        User user = new User("John", "Doe", "password");
        String stockSymbol = "AAPL";
        int quantity = -10;
        double price = 150.0;

        when(mockDataAccess.getQuantity(user.getUserID(), stockSymbol)).thenReturn(5);

        BrokerageInputData input = new BrokerageInputData(user, stockSymbol, quantity, price);

        // When
        interactor.tradeStock(input);

        // Then
        verify(mockOutputBoundary).prepareFailView("You don't have enough shares. You currently have 5 shares of AAPL. ");
        verifyNoInteractions(mockStockFactory);
    }

    @Test
    void testTradeStockInsufficientBalance() {
        // Given
        User user = new User("John", "Doe", "password");
        user.setBalance(100.0);
        String stockSymbol = "AAPL";
        int quantity = 2;
        double price = 150.0;

        when(mockDataAccess.getQuantity(user.getUserID(), stockSymbol)).thenReturn(5);

        BrokerageInputData input = new BrokerageInputData(user, stockSymbol, quantity, price);

        // When
        interactor.tradeStock(input);

        // Then
        verify(mockOutputBoundary).prepareFailView("You don't have enough balance. ");
        verifyNoInteractions(mockStockFactory);
    }
}
