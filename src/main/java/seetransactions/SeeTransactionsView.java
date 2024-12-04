package seetransactions;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import data_access.DBTransactionDataAccessObject;
import entity.Transaction;
import entity.User;
import loggedin.interface_adapter.LoggedinState;
import seetransactions.interface_adapter.SeeTransactionsController;
import seetransactions.interface_adapter.SeeTransactionsState;
import seetransactions.interface_adapter.SeeTransactionsViewModel;
import seetransactions.use_case.SeeTransactionsDataAccessInterface;

/**
 * The View for when the user is logged into the program.
 */
public class SeeTransactionsView extends JPanel implements ActionListener, PropertyChangeListener {
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 400;
    private static final int TABLE_WIDTH = 400;
    private static final int TABLE_HEIGHT = 300;
    private static final String FORMAT = "%.2f";

    private final String viewName = "seetransactions";
    private final SeeTransactionsViewModel seeTransactionsViewModel;
    private final SeeTransactionsController seeTransactionsController;
    private final SeeTransactionsDataAccessInterface seeTransactionsDataAccessInterface = new DBTransactionDataAccessObject();

    private User loggedinUser;
    private final JButton cancel;
    private DefaultTableModel tableModel;
    private JTable transactionTable;
    private JScrollPane scrollPane;

    public SeeTransactionsView(SeeTransactionsController seeTransactionsController,
                               SeeTransactionsViewModel seeTransactionsViewModel) {
        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        this.seeTransactionsController = seeTransactionsController;
        this.seeTransactionsViewModel = seeTransactionsViewModel;
        seeTransactionsViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("See Transactions in Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final String[] columnNames = {"ID", "Card", "Receiver", "Amount $"};
        tableModel = new DefaultTableModel(columnNames, 0);
        transactionTable = new JTable(tableModel);
        scrollPane = new JScrollPane(transactionTable);
        scrollPane.setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));

        final JPanel buttons = new JPanel();
        cancel = new JButton("Cancel");
        buttons.add(cancel);

        cancel.addActionListener(this);


        this.add(title, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(buttons, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
        if ("Cancel".equals(evt.getActionCommand())) {
            seeTransactionsController.switchToLoggedinView();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SeeTransactionsState state = (SeeTransactionsState) evt.getNewValue();
        loggedinUser = state.getUser();
        setFields();
        seeTransactionsViewModel.setState(state);
    }

    private void setFields() {
        final String[] columnNames = {"ID", "Card", "Receiver", "Amount $"};
        tableModel = new DefaultTableModel(columnNames, 0);

        final List<Transaction> transactions = seeTransactionsDataAccessInterface.getTransactions(loggedinUser);
        for (Transaction transaction : transactions) {
            final String[] rowData = {
                    String.valueOf(transaction.getTransactionID()),
                    transaction.getCardUsed(),
                    String.valueOf(transaction.getReceiverID()),
                    String.format(FORMAT, transaction.getAmount()),
            };
            tableModel.addRow(rowData);
        }

        transactionTable.setModel(tableModel);

        if (scrollPane != null) {
            remove(scrollPane);
        }

        scrollPane = new JScrollPane(transactionTable);
        add(scrollPane, BorderLayout.CENTER);

        revalidate();
        repaint();
    }


    public String getViewName() {
        return viewName;
    }
}
