package brokerage;

import brokerage.interface_adapter.BrokerageController;
import brokerage.interface_adapter.BrokerageState;
import brokerage.interface_adapter.BrokerageViewModel;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import data_access.DBBrokerageDataAccessObject;
import entity.User;
import maketransaction.interface_adapter.MakeTransactionState;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import view.LabelTextPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * The {@code BrokerageView} class provides a graphical user interface (GUI) for managing stock assets.
 */
public class BrokerageView extends JPanel implements ActionListener, PropertyChangeListener {
    private static final int ROW_COUNT = 4;
    private static final int COLUMN_COUNT = 2;
    private static final int HORIZONTAL_GAP = 10;
    private static final int VERTICAL_GAP = 10;
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 400;

    private final String viewName = "brokerage";
    private final BrokerageViewModel brokerageViewModel;
    private final BrokerageController brokerageController;
    private final DBBrokerageDataAccessObject brokerageDataAccessObject = new DBBrokerageDataAccessObject();

    private final JTextField stockNameInputField = new JTextField(10);
    private JTextField stockPriceLabel = new JTextField(10);
    private final JButton searchStockButton;
    private final JButton buyButton;
    private final JButton sellButton;
    private final JButton cancelButton;
    private final JSpinner amountSpinner;
    private final JPanel graphPanel;
    private final JPanel bottomPanel;

    private User loggedinUser;

    public BrokerageView(BrokerageController brokerageController, BrokerageViewModel brokerageViewModel) {
        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        this.brokerageViewModel = brokerageViewModel;
        this.brokerageController = brokerageController;
        this.brokerageViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Crazy Bank - Asset Managing");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title, BorderLayout.NORTH);

        final JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        final LabelTextPanel stockSymbolInfo = new LabelTextPanel(
                new JLabel("Stock symbol:"), stockNameInputField);
        searchStockButton = new JButton("Search");
        topPanel.add(stockSymbolInfo);
        topPanel.add(stockNameInputField);
        topPanel.add(searchStockButton);
        add(topPanel, BorderLayout.NORTH);

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.X_AXIS));
        JLabel priceLabel = new JLabel("Price $:");
        middlePanel.add(priceLabel);
        middlePanel.add(stockPriceLabel);
        add(middlePanel, BorderLayout.CENTER);

        graphPanel = createGraphPanel();
        add(graphPanel, BorderLayout.CENTER);

        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        final int amountOwned = brokerageDataAccessObject.getQuantity();
        amountSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        buyButton = new JButton("Buy");
        sellButton = new JButton("Sell");
        cancelButton = new JButton("Cancel");

        searchStockButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        if (event.getSource().equals(searchStockButton)) {
                            BrokerageState state = brokerageViewModel.getState();
                            brokerageController.searchStock(state.getStockSymbol());
                        }
                    }
                }
        );

        buyButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        if (event.getSource().equals(buyButton)) {
                            BrokerageState state = brokerageViewModel.getState();
                            brokerageController.tradeStock(state.getUser(), state.getStockSymbol(), state.getQuantity());
                        }
                    }
                }
        );

        sellButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        if (event.getSource().equals(buyButton)) {
                            BrokerageState state = brokerageViewModel.getState();
                            brokerageController.tradeStock(state.getUser(), state.getStockSymbol(), -1 * state.getQuantity());
                        }
                    }
                }
        );

        cancelButton.addActionListener(this);

        stockNameInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final BrokerageState state = brokerageViewModel.getState();
                state.setStockSymbol(stockNameInputField.getText());
                brokerageViewModel.setState(state);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        amountSpinner.addChangeListener(evt -> {
            final BrokerageState state = brokerageViewModel.getState();
            state.setQuantity((Integer) amountSpinner.getValue());
            brokerageViewModel.setState(state);
        });
    }

    private JPanel createGraphPanel() {
        final JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Stock Price Trends"));
        return panel;
    }

    /**
    private static void sellButtonAction(BrokerageController controller, JTextField stockNameField) {
        final String stockSymbol = stockNameField.getText().trim();
        final double latestPrice = controller.onSearchStockSuccess(stockSymbol);

        final int amountOwn = controller.getQuantity(stockSymbol);

        final JSpinner amountSpinner = new JSpinner(new SpinnerNumberModel(0, 0, amountOwn, 1));
        final JLabel l = new JLabel("Total Ask Price: " + latestPrice);
        amountSpinner.addChangeListener(event -> {
            l.setText(String.format("Total Ask Price: %.2f", (Integer) amountSpinner.getValue() * latestPrice)); });

        final JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Enter amount of security to sell:"));
        panel.add(amountSpinner);
        panel.add(l);

        final int option = JOptionPane.showConfirmDialog(
                null,
                panel,
                "Sell Asset",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option == JOptionPane.OK_OPTION) {
            final int amount = (int) amountSpinner.getValue();
            if (amount > 0) {
                final boolean success = controller.onSellTriggered(stockSymbol, amount);
                if (success) {
                    controller.addStock(stockSymbol, -1 * amount, latestPrice);
                    JOptionPane.showMessageDialog(
                            null,
                            "You have sold " + amount + " shares of " + stockSymbol,
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
            else {
                JOptionPane.showMessageDialog(
                        null,
                        "Please enter a valid amount.",
                        "Invalid Amount",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private static void buyButtonAction(BrokerageController controller, JTextField stockNameField) {
        final String stockSymbol = stockNameField.getText().trim();
        final double latestPrice = controller.onSearchStockSuccess(stockSymbol);

        final JSpinner amountSpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        final JLabel l = new JLabel("Total Bid Price: " + latestPrice);
        amountSpinner.addChangeListener(event -> {
            l.setText(String.format("Total Bid Price: %.2f", (Integer) amountSpinner.getValue() * latestPrice)); });

        final JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Enter amount of security to buy:"));
        panel.add(amountSpinner);
        panel.add(l);

        final int option = JOptionPane.showConfirmDialog(
                null,
                panel,
                "Buy Asset",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option == JOptionPane.OK_OPTION) {
            final int amount = (int) amountSpinner.getValue();
            if (amount > 0) {
                final boolean success = controller.onBuyTriggered(amount, latestPrice);
                if (success) {
                    controller.addStock(stockSymbol, amount, latestPrice);
                    JOptionPane.showMessageDialog(
                            null,
                            "You have bought " + amount + " shares of " + stockSymbol,
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
                else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Insufficient funds to buy the asset.",
                            "Insufficient funds",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
            else {
                JOptionPane.showMessageDialog(
                        null,
                        "Please enter a valid amount greater than zero.",
                        "Invalid Amount",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void searchButtonAction(BrokerageController controller, JTextField stockNameFieldPassed,
                                    JLabel stockPriceLabelPassed, JButton buyButtonPassed, JButton sellButtonPassed) {
        final String stockSymbol = stockNameFieldPassed.getText().trim();
        final boolean success1 = controller.onSearchStockTriggered(stockSymbol);

        if (success1) {
            final boolean success2 = controller.isStockFound(stockSymbol);
            if (success2) {
                final List<StockUnit> stocks = controller.retrieveStocks(stockSymbol);
                final double stockPrice = controller.onSearchStockSuccess(stockSymbol);
                stockPriceLabelPassed.setText(String.format("$%.2f", stockPrice));
                buyButtonPassed.setEnabled(true);
                sellButtonPassed.setEnabled(true);
                drawGraph(stockSymbol, stocks);
            }
            else {
                JOptionPane.showMessageDialog(null,
                        "No data found for this stock symbol.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else {
            JOptionPane.showMessageDialog(null,
                    "Please enter a stock symbol.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void drawGraph(String stockSymbol, List<StockUnit> stockUnits) {
        final TimeSeries series = new TimeSeries(stockSymbol);
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (StockUnit unit : stockUnits) {
            try {
                final java.util.Date date = dateFormat.parse(unit.getDate());
                series.addOrUpdate(new Second(date), unit.getClose());
            }
            catch (ParseException event) {
                throw new RuntimeException(event);
            }
        }

        final TimeSeriesCollection dataset = new TimeSeriesCollection(series);

        final JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Stock Price Trends - " + stockSymbol,
                "Time",
                "Price ($)",
                dataset,
                false,
                true,
                false
        );

        final XYPlot plot = (XYPlot) chart.getPlot();
        plot.getRenderer().setSeriesPaint(0, Color.decode("#222d64"));
        plot.getRenderer().setSeriesStroke(0, new BasicStroke(2.0f));

        final ChartPanel chartPanel = new ChartPanel(chart);
        final JPanel panel = this.getGraphPanel();
        panel.removeAll();
        panel.add(chartPanel, BorderLayout.CENTER);
        panel.validate();
    }
     */

    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
        if ("Cancel".equals(evt.getActionCommand())) {
            brokerageController.switchToLoggedinView();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final BrokerageState state = (BrokerageState) evt.getNewValue();
        setFields(state);
    }

    private void setFields(BrokerageState state) {
        stockPriceLabel.setText(String.valueOf(state.getPrice()));
        drawGraph(state.getStockSymbol(), state.getStocks());
    }

    private void drawGraph(String stockSymbol, List<StockUnit> stockUnits) {
        final TimeSeries series = new TimeSeries(stockSymbol);
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (StockUnit unit : stockUnits) {
            try {
                final java.util.Date date = dateFormat.parse(unit.getDate());
                series.addOrUpdate(new Second(date), unit.getClose());
            }
            catch (ParseException event) {
                throw new RuntimeException(event);
            }
        }

        final TimeSeriesCollection dataset = new TimeSeriesCollection(series);

        final JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Stock Price Trends - " + stockSymbol,
                "Time",
                "Price ($)",
                dataset,
                false,
                true,
                false
        );

        final XYPlot plot = (XYPlot) chart.getPlot();
        plot.getRenderer().setSeriesPaint(0, Color.decode("#222d64"));
        plot.getRenderer().setSeriesStroke(0, new BasicStroke(2.0f));

        final ChartPanel chartPanel = new ChartPanel(chart);
        final JPanel panel = graphPanel;
        panel.removeAll();
        panel.add(chartPanel, BorderLayout.CENTER);
        panel.validate();

        bottomPanel.add(amountSpinner);
        bottomPanel.add(buyButton);
        bottomPanel.add(sellButton);
        bottomPanel.add(cancelButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }


    public String getViewName() {
        return viewName;
    }
}
