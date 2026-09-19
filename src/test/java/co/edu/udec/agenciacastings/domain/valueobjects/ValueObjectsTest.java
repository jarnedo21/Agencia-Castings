package co.edu.udec.agenciacastings.domain.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValueObjectsTest {

    @Test
    void idsConElMismoValorSonIguales() {
        assertEquals(new ClienteID("abc"), new ClienteID("abc"));
        assertEquals(new ClienteID("abc").hashCode(), new ClienteID("abc").hashCode());
        assertNotEquals(new ClienteID(), new ClienteID());
    }

    @Test
    void codigoVacioLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new CodigoCasting(" "));
        assertThrows(IllegalArgumentException.class, () -> new DNI(null));
    }

    @Test
    void rangoAlturaRecibeMinimoYLuegoMaximo() {
        RangoAltura rango = new RangoAltura(1.60, 1.80);
        assertEquals(1.60, rango.getAlturaMinima());
        assertEquals(1.80, rango.getAlturaMaxima());
        assertTrue(rango.incluye(1.75));
        assertThrows(IllegalArgumentException.class, () -> new RangoAltura(1.90, 1.60));
    }

    @Test
    void rangoEdadInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new RangoEdad(30, 18));
        assertThrows(IllegalArgumentException.class, () -> new RangoEdad(-1, 18));
    }

    @Test
    void costoNegativoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Costo(-1));
    }
}
