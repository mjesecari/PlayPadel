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

//    @Test
//    void testSetCijena0ClanarineNotThrows() {
//        Administrator admin = new Administrator();
//        assertThrows(IllegalArgumentException.class,
//                () -> {
//                    admin.setCijenaClanarine(0.F);
//                });
//    }

}