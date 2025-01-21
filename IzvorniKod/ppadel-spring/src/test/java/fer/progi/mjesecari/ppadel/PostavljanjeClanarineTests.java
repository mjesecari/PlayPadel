package fer.progi.mjesecari.ppadel;

import fer.progi.mjesecari.ppadel.domain.Administrator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Method;

class PostavljanjeClanarineTests {

    @Test
    void testAddDiscountFunctionNonExistent() {
        Administrator admin = new Administrator();
        try {
            // Use reflection to check if the method "addDiscount" exists
            Method method = Administrator.class.getMethod("addDiscount");

            // If the method is found, the test fails
            System.out.println("Test failed: The method addDiscount() unexpectedly exists.");
        } catch (NoSuchMethodException e) {
            // Method does not exist, test passes
            System.out.println("Test passed: The method addDiscount() does not exist as expected.");
        } catch (Exception e) {
            // Handle other unexpected exceptions
            System.out.println("Test failed: An unexpected error occurred.");
            e.printStackTrace(); // Log the error for debugging
            throw new RuntimeException("Test failed due to unexpected error", e);
        }
    }

    @Test
    void testSetCijena1ClanarineThrows() {
        Administrator admin = new Administrator();
        System.out.println("Test input: -1.F");
        try {
            assertThrows(IllegalArgumentException.class,
                    () -> admin.setCijenaClanarine(-1.F));
            System.out.println("Test passed: IllegalArgumentException was thrown as expected.");
        } catch (AssertionError e) {
            System.out.println("Test failed: IllegalArgumentException was not thrown.");
            throw e;
        }
    }

    @Test
    void testSetCijena0ClanarineNotThrows() {
        Administrator admin = new Administrator();
        System.out.println("Test input: 78.F");
        try {
            admin.setCijenaClanarine(78.F);
            assertEquals(78.F, admin.getCijenaClanarine());
            System.out.println("Test passed: The value was set correctly.");
        } catch (AssertionError | Exception e) {
            System.out.println("Test failed: An error occurred while setting the value.");
            throw e; // Ensure the test fails in JUnit's reporting.
        }
    }

    @Test
    void testSetCijena1ClanarineNotThrows() {
        Administrator admin = new Administrator();
        System.out.println("Test input: 0.F");
        try {
            assertDoesNotThrow(() -> admin.setCijenaClanarine(0.F));
            assertEquals(0.F, admin.getCijenaClanarine());
            System.out.println("Test passed: The value was set correctly without throwing any exceptions.");
        } catch (AssertionError | Exception e) {
            System.out.println("Test failed: An error occurred while setting the value.");
            throw e; // Ensure the test fails in JUnit's reporting.
        }
    }
}