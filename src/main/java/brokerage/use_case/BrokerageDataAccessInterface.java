package brokerage.use_case;

import entity.Stock;
import entity.User;

/**
 * An interface for accessing and managing brokerage data.
 * This interface defines methods for retrieving stock quantities
 * and saving user-stock data.
 */
public interface BrokerageDataAccessInterface {

    /**
     * Retrieves the quantity of a specific stock owned by a user.
     *
     * @param userID   the unique identifier of the user
     * @param stockID  the unique identifier of the stock
     * @return the quantity of the stock owned by the user, or 0 if the user does not own the stock
     */
    int getQuantity(int userID, String stockID);


    /**
     * Saves the stock data for a specific user.
     *
     * @param userID  the unique identifier of the user
     * @param stock   the stock entity to be saved
     * @return the updated user entity after saving the stock data
     */
    User saveData(int userID, Stock stock);
}
