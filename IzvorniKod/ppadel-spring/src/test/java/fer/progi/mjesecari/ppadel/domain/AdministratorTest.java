package fer.progi.mjesecari.ppadel.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdministratorTest {



    @Test
    void testSetCijena1ClanarineThrows() {
        Administrator admin = new Administrator();

        try {
            assertThrows(IllegalArgumentException.class,
                    () -> admin.setCijenaClanarine(-1.F));
            System.out.println("200 - Test passed: IllegalArgumentException was thrown as expected.");
        } catch (AssertionError e) {
            System.out.println("400 - Test failed: IllegalArgumentException was not thrown.");
            throw e;
        }
    }

    @Test
    void testSetCijena0ClanarineNotThrows() {
        Administrator admin = new Administrator();
        try {
            admin.setCijenaClanarine(78.F);
            assertEquals(78.F, admin.getCijenaClanarine());
            System.out.println("200 - Test passed: The value was set correctly.");
        } catch (AssertionError | Exception e) {
            System.out.println("400 - Test failed: An error occurred while setting the value.");
            throw e; // Ensure the test fails in JUnit's reporting.
        }
    }

    @Test
    void testSetCijena1ClanarineNotThrows() {
        Administrator admin = new Administrator();

        try {
            assertDoesNotThrow(() -> admin.setCijenaClanarine(0.F));
            assertEquals(0.F, admin.getCijenaClanarine());
            System.out.println("200 - Test passed: The value was set correctly without throwing any exceptions.");
        } catch (AssertionError | Exception e) {
            System.out.println("400 - Test failed: An error occurred while setting the value.");
            throw e; // Ensure the test fails in JUnit's reporting.
        }
    }
}