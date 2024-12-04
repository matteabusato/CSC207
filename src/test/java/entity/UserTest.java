package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("John", "Doe", "hashed_password");
    }

    @Test
    void testConstructorWithDefaultValues() {
        User userWithDefaults = new User("Jane", "Smith", "hashed_password");
        assertEquals("Jane", userWithDefaults.getFirstName());
        assertEquals("Smith", userWithDefaults.getLastName());
        assertEquals("hashed_password", userWithDefaults.getPasswordHash());
        assertEquals(0.0, userWithDefaults.getBalance(), "Balance should be initialized to 0.");
        assertTrue(userWithDefaults.getFileDirectory().contains("JaneSmith"),
                "File directory should contain concatenated first and last name.");
    }

    @Test
    void testSetBalance() {
        double newBalance = 150.75;
        user.setBalance(newBalance);
        assertEquals(newBalance, user.getBalance(), "Balance should match the new value set.");
    }

    @Test
    void testGenerateUserID() {
        int generatedID = user.generateUserID();
        assertTrue(generatedID >= 10001, "Generated user ID should be equal to or greater than the starting value.");
    }

    @Test
    void testGenerateStartingBalance() {
        double startingBalance = user.generateStartingBalance();
        assertEquals(0.0, startingBalance, "Starting balance should always be 0.");
    }

    @Test
    void testGenerateFileDirectory() {
        String fileDirectory = user.generateFileDirectory();
        assertTrue(fileDirectory.contains("JohnDoe"),
                "File directory should contain concatenated first name and last name.");
        assertTrue(fileDirectory.contains(String.valueOf(user.getUserID())),
                "File directory should include the user's ID.");
    }
}
