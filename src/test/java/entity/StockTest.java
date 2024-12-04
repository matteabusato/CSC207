package entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockTest {

    @Test
    void testDefaultConstructor() {
        // Given
        Stock stock = new Stock();

        // Then
        assertNull(stock.getStockID(), "Default stockID should be null.");
        assertEquals(0, stock.getQuantity(), "Default quantity should be 0.");
        assertEquals(0.0, stock.getPrice(), "Default price should be 0.0.");
    }

    @Test
    void testParameterizedConstructor() {
        // Given
        String stockID = "STK123";
        int quantity = 50;
        double price = 150.75;

        // When
        Stock stock = new Stock(stockID, quantity, price);

        // Then
        assertEquals(stockID, stock.getStockID(), "StockID should match the input value.");
        assertEquals(quantity, stock.getQuantity(), "Quantity should match the input value.");
        assertEquals(price, stock.getPrice(), "Price should match the input value.");
    }

    @Test
    void testSetQuantity() {
        // Given
        Stock stock = new Stock("STK123", 50, 150.75);
        int newQuantity = 75;

        // When
        stock.setQuantity(newQuantity);

        // Then
        assertEquals(newQuantity, stock.getQuantity(), "Quantity should be updated to the new value.");
    }

    @Test
    void testSetPrice() {
        // Given
        Stock stock = new Stock("STK123", 50, 150.75);
        double newPrice = 200.50;

        // When
        stock.setPrice(newPrice);

        // Then
        assertEquals(newPrice, stock.getPrice(), "Price should be updated to the new value.");
    }
}
