package co.edu.udec.agenciacastings.application;

import co.edu.udec.agenciacastings.Main;
import co.edu.udec.agenciacastings.application.exceptions.EntidadNoEncontradaException;
import co.edu.udec.agenciacastings.application.exceptions.RegistroDuplicadoException;
import co.edu.udec.agenciacastings.domain.enums.EstadoResultado;
import co.edu.udec.agenciacastings.domain.enums.TipoActividad;
import co.edu.udec.agenciacastings.domain.enums.TipoCasting;
import co.edu.udec.agenciacastings.domain.exceptions.CastingSinClienteException;
import co.edu.udec.agenciacastings.domain.exceptions.SeleccionException;
import co.edu.udec.agenciacastings.domain.model.Candidato;
import co.edu.udec.agenciacastings.domain.model.Casting;
import co.edu.udec.agenciacastings.domain.model.Cliente;
import co.edu.udec.agenciacastings.domain.model.Fase;
import co.edu.udec.agenciacastings.domain.model.Prueba;
import co.edu.udec.agenciacastings.domain.model.Resultado;
import co.edu.udec.agenciacastings.domain.model.Seleccion;
import co.edu.udec.agenciacastings.domain.valueobjects.CandidatoID;
import co.edu.udec.agenciacastings.domain.valueobjects.CastingID;
import co.edu.udec.agenciacastings.domain.valueobjects.ClienteID;
import co.edu.udec.agenciacastings.domain.valueobjects.CodigoCandidato;
import co.edu.udec.agenciacastings.domain.valueobjects.CodigoCasting;
import co.edu.udec.agenciacastings.domain.valueobjects.CodigoCliente;
import co.edu.udec.agenciacastings.domain.valueobjects.Costo;
import co.edu.udec.agenciacastings.domain.valueobjects.FaseID;
import co.edu.udec.agenciacastings.domain.valueobjects.PruebaID;
import co.edu.udec.agenciacastings.domain.valueobjects.ResultadoID;
import co.edu.udec.agenciacastings.domain.valueobjects.SeleccionID;
import co.edu.udec.agenciacastings.infrastructure.config.DatosDeEjemplo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AgenciaCastingServiceTest {

    private AgenciaCastingService servicio;
    private Cliente cliente;
    private Casting casting;
    private Prueba prueba;

    @BeforeEach
    void preparar() {
        servicio = Main.crearServicio();
        cliente = new Cliente(new ClienteID(), new CodigoCliente("CLI-1"), "Cliente", "Dir", "300", "Contacto",
                TipoActividad.PUBLICIDAD_CINE);
        servicio.registrarCliente(cliente);
        casting = crearCasting("CAS-1", cliente.getId(), 1);
        servicio.registrarCasting(casting);
        Fase fase = new Fase(new FaseID(), 1, LocalDate.now(), casting.getId());
        servicio.registrarFase(fase);
        prueba = new Prueba(new PruebaID(), 1, LocalDate.now(), "Sala 1", "Monologo", fase.getId());
        servicio.registrarPrueba(prueba);
    }

    private Casting crearCasting(String codigo, ClienteID clienteId, int personas) {
        return new Casting(new CastingID(), new CodigoCasting(codigo), "Comercial", "Desc", LocalDate.now(),
                new Costo(1000), TipoCasting.ONLINE, clienteId, personas);
    }

    private Candidato registrarCandidato(String codigo) {
        Candidato candidato = new Candidato(new CandidatoID(), new CodigoCandidato(codigo), "Nombre " + codigo,
                "Dir", "300", LocalDate.of(1995, 1, 1), null, null);
        servicio.registrarCandidato(candidato);
        return candidato;
    }

    private void registrarResultado(Candidato candidato, EstadoResultado estado) {
        servicio.registrarResultado(new Resultado(new ResultadoID(), candidato.getId(), prueba.getId(), estado));
    }

    private Seleccion seleccion(Candidato candidato) {
        return new Seleccion(new SeleccionID(), candidato.getId(), casting.getId(), LocalDate.now());
    }

    @Test
    void castingSinClienteNoSePuedeCrear() {
        assertThrows(CastingSinClienteException.class, () -> crearCasting("X", null, 1));
    }

    @Test
    void castingConClienteInexistenteNoSeRegistra() {
        assertThrows(EntidadNoEncontradaException.class,
                () -> servicio.registrarCasting(crearCasting("CAS-2", new ClienteID(), 1)));
    }

    @Test
    void codigoDeClienteDuplicado() {
        Cliente otro = new Cliente(new ClienteID(), new CodigoCliente("CLI-1"), "Otro", "Dir", "300", "C",
                TipoActividad.MODA);
        assertThrows(RegistroDuplicadoException.class, () -> servicio.registrarCliente(otro));
    }

    @Test
    void candidatoAprobadoPuedeSerSeleccionado() {
        Candidato candidato = registrarCandidato("CAN-1");
        registrarResultado(candidato, EstadoResultado.APROBADO);

        assertDoesNotThrow(() -> servicio.registrarSeleccion(seleccion(candidato)));
        assertEquals(1, servicio.candidatosSeleccionados(casting.getId()).size());
    }

    @Test
    void candidatoReprobadoOSinResultadosNoPuedeSerSeleccionado() {
        Candidato reprobado = registrarCandidato("CAN-1");
        registrarResultado(reprobado, EstadoResultado.NO_APROBADO);
        Candidato sinResultados = registrarCandidato("CAN-2");

        assertThrows(SeleccionException.class, () -> servicio.registrarSeleccion(seleccion(reprobado)));
        assertThrows(SeleccionException.class, () -> servicio.registrarSeleccion(seleccion(sinResultados)));
    }

    @Test
    void noSeSuperaElCupoDelCasting() {
        Candidato primero = registrarCandidato("CAN-1");
        Candidato segundo = registrarCandidato("CAN-2");
        registrarResultado(primero, EstadoResultado.APROBADO);
        registrarResultado(segundo, EstadoResultado.APROBADO);
        servicio.registrarSeleccion(seleccion(primero));

        assertThrows(SeleccionException.class, () -> servicio.registrarSeleccion(seleccion(segundo)));
    }

    @Test
    void resultadoDuplicadoParaLaMismaPrueba() {
        Candidato candidato = registrarCandidato("CAN-1");
        registrarResultado(candidato, EstadoResultado.APROBADO);
        assertThrows(RegistroDuplicadoException.class,
                () -> registrarResultado(candidato, EstadoResultado.NO_APROBADO));
    }

    @Test
    void datosDeEjemploSeCarganSinErrores() {
        AgenciaCastingService nuevo = Main.crearServicio();
        assertDoesNotThrow(() -> DatosDeEjemplo.cargar(nuevo));
        assertEquals(3, nuevo.listarCandidatos().size());
        assertEquals(1, nuevo.candidatosMenoresDeEdad().size());
    }
}
