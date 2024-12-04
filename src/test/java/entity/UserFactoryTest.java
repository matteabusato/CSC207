package entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserFactoryTest {

    private UserFactory userFactory;

    @BeforeEach
    void setUp() {
        userFactory = new UserFactory();
    }

    @Test
    void testCreateUser() {
        // Given
        String firstName = "John";
        String lastName = "Doe";
        String passwordHash = "hashedPassword123";

        // When
        User user = userFactory.create(firstName, lastName, passwordHash);

        // Then
        assertNotNull(user, "User should not be null.");
        assertEquals(firstName, user.getFirstName(), "First name should match.");
        assertEquals(lastName, user.getLastName(), "Last name should match.");
        assertEquals(passwordHash, user.getPasswordHash(), "Password hash should match.");
    }
}
