package co.edu.udec.agenciacastings.domain.model;

import co.edu.udec.agenciacastings.domain.exceptions.CandidatoException;
import co.edu.udec.agenciacastings.domain.exceptions.CandidatoMenorSinTutorException;
import co.edu.udec.agenciacastings.domain.valueobjects.CandidatoID;
import co.edu.udec.agenciacastings.domain.valueobjects.CodigoCandidato;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CandidatoTest {

    private Candidato crear(LocalDate fechaNacimiento, String tutor) {
        return new Candidato(new CandidatoID(), new CodigoCandidato("C-1"), "Ana", "Calle 1", "300",
                fechaNacimiento, "foto.jpg", tutor);
    }

    @Test
    void adultoSinTutorEsValido() {
        Candidato candidato = crear(LocalDate.now().minusYears(25), null);
        assertFalse(candidato.esMenorDeEdad());
        assertEquals(25, candidato.getEdad());
    }

    @Test
    void menorSinTutorLanzaExcepcion() {
        assertThrows(CandidatoMenorSinTutorException.class, () -> crear(LocalDate.now().minusYears(15), null));
        assertThrows(CandidatoMenorSinTutorException.class, () -> crear(LocalDate.now().minusYears(15), " "));
    }

    @Test
    void menorConTutorEsValido() {
        assertTrue(crear(LocalDate.now().minusYears(15), "Marta").esMenorDeEdad());
    }

    @Test
    void menorNoPuedeQuedarSinTutor() {
        Candidato candidato = crear(LocalDate.now().minusYears(15), "Marta");
        assertThrows(CandidatoMenorSinTutorException.class, () -> candidato.cambiarTutor(null));
    }

    @Test
    void fechaDeNacimientoObligatoriaYNoFutura() {
        assertThrows(CandidatoException.class, () -> crear(null, null));
        assertThrows(CandidatoException.class, () -> crear(LocalDate.now().plusDays(1), "Tutor"));
    }

    @Test
    void cambiarDireccionVaciaLanzaExcepcion() {
        Candidato candidato = crear(LocalDate.now().minusYears(30), null);
        CandidatoException e = assertThrows(CandidatoException.class, () -> candidato.cambiarDireccion(""));
        assertEquals("La direccion no puede estar vacia", e.getMessage());
    }
}
