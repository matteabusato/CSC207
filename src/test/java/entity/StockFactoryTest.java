package entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockFactoryTest {

    @Test
    void testCreateStock() {
        // Given
        StockFactory stockFactory = new StockFactory();
        String stockID = "STK001";
        int quantity = 100;
        double price = 250.50;

        // When
        Stock stock = stockFactory.create(stockID, quantity, price);

        // Then
        assertNotNull(stock, "The created stock should not be null.");
        assertEquals(stockID, stock.getStockID(), "StockID should match the input value.");
        assertEquals(quantity, stock.getQuantity(), "Quantity should match the input value.");
        assertEquals(price, stock.getPrice(), "Price should match the input value.");
    }

    @Test
    void testCreateStockWithZeroQuantity() {
        // Given
        StockFactory stockFactory = new StockFactory();
        String stockID = "STK002";
        int quantity = 0;
        double price = 100.0;

        // When
        Stock stock = stockFactory.create(stockID, quantity, price);

        // Then
        assertNotNull(stock, "The created stock should not be null.");
        assertEquals(stockID, stock.getStockID(), "StockID should match the input value.");
        assertEquals(quantity, stock.getQuantity(), "Quantity should be 0.");
        assertEquals(price, stock.getPrice(), "Price should match the input value.");
    }

    @Test
    void testCreateStockWithNegativePrice() {
        // Given
        StockFactory stockFactory = new StockFactory();
        String stockID = "STK003";
        int quantity = 10;
        double price = -50.0;

        // When
        Stock stock = stockFactory.create(stockID, quantity, price);

        // Then
        assertNotNull(stock, "The created stock should not be null.");
        assertEquals(stockID, stock.getStockID(), "StockID should match the input value.");
        assertEquals(quantity, stock.getQuantity(), "Quantity should match the input value.");
        assertEquals(price, stock.getPrice(), "Price should match the input value.");
    }
}
