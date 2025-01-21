package fer.progi.mjesecari.ppadel.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdministratorTest {

    @Test
    void testSetCijena1ClanarineThrows() {
        Administrator admin = new Administrator();
        assertThrows(IllegalArgumentException.class,
                () -> {
            admin.setCijenaClanarine(-1.F);
        });
    }

    @Test
    void testSetCijena0ClanarineNotThrows() {
        Administrator admin = new Administrator();
        admin.setCijenaClanarine(78.F);
        assertEquals(78.F, admin.getCijenaClanarine());
    }

    @Test
    void testSetCijena1ClanarineNotThrows() {
        Administrator admin = new Administrator();
        assertDoesNotThrow(() -> {
            admin.setCijenaClanarine(0.F);
        });
        assertEquals(0.F, admin.getCijenaClanarine());
    }
}