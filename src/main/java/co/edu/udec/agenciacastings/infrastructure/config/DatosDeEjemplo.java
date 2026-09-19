package co.edu.udec.agenciacastings.infrastructure.config;

import co.edu.udec.agenciacastings.application.AgenciaCastingService;
import co.edu.udec.agenciacastings.domain.enums.Especialidad;
import co.edu.udec.agenciacastings.domain.enums.EstadoResultado;
import co.edu.udec.agenciacastings.domain.enums.Sexo;
import co.edu.udec.agenciacastings.domain.enums.TipoActividad;
import co.edu.udec.agenciacastings.domain.enums.TipoCasting;
import co.edu.udec.agenciacastings.domain.model.AgenteCasting;
import co.edu.udec.agenciacastings.domain.model.Candidato;
import co.edu.udec.agenciacastings.domain.model.Casting;
import co.edu.udec.agenciacastings.domain.model.Cliente;
import co.edu.udec.agenciacastings.domain.model.Fase;
import co.edu.udec.agenciacastings.domain.model.Perfil;
import co.edu.udec.agenciacastings.domain.model.Prueba;
import co.edu.udec.agenciacastings.domain.model.Representante;
import co.edu.udec.agenciacastings.domain.model.Resultado;
import co.edu.udec.agenciacastings.domain.valueobjects.AgenteCastingID;
import co.edu.udec.agenciacastings.domain.valueobjects.CandidatoID;
import co.edu.udec.agenciacastings.domain.valueobjects.CastingID;
import co.edu.udec.agenciacastings.domain.valueobjects.ClienteID;
import co.edu.udec.agenciacastings.domain.valueobjects.CodigoCandidato;
import co.edu.udec.agenciacastings.domain.valueobjects.CodigoCasting;
import co.edu.udec.agenciacastings.domain.valueobjects.CodigoCliente;
import co.edu.udec.agenciacastings.domain.valueobjects.Costo;
import co.edu.udec.agenciacastings.domain.valueobjects.DNI;
import co.edu.udec.agenciacastings.domain.valueobjects.FaseID;
import co.edu.udec.agenciacastings.domain.valueobjects.PerfilID;
import co.edu.udec.agenciacastings.domain.valueobjects.PruebaID;
import co.edu.udec.agenciacastings.domain.valueobjects.RangoAltura;
import co.edu.udec.agenciacastings.domain.valueobjects.RangoEdad;
import co.edu.udec.agenciacastings.domain.valueobjects.RepresentanteID;
import co.edu.udec.agenciacastings.domain.valueobjects.ResultadoID;

import java.time.LocalDate;

/**
 * Carga un conjunto de datos de prueba para no tener que digitarlos a mano.
 */
public final class DatosDeEjemplo {

    private DatosDeEjemplo() {
    }

    public static void cargar(AgenciaCastingService servicio) {
        Cliente cliente = new Cliente(new ClienteID(), new CodigoCliente("CLI-001"), "Moda Andina S.A.S.",
                "Calle 10 # 5-20", "3001234567", "Laura Gomez", TipoActividad.MODA);
        servicio.registrarCliente(cliente);

        Casting casting = new Casting(new CastingID(), new CodigoCasting("CAS-001"), "Pasarela Verano",
                "Modelos para desfile de coleccion verano", LocalDate.now().minusDays(10), new Costo(2_500_000),
                TipoCasting.PRESENCIAL, cliente.getId(), 2);
        servicio.registrarCasting(casting);

        Fase fase1 = new Fase(new FaseID(), 1, LocalDate.now().minusDays(5), casting.getId());
        servicio.registrarFase(fase1);
        Prueba pasarela = new Prueba(new PruebaID(), 1, LocalDate.now().minusDays(4), "Sala A",
                "Prueba de pasarela", fase1.getId());
        Prueba fotos = new Prueba(new PruebaID(), 2, LocalDate.now().minusDays(3), "Estudio 2",
                "Sesion de fotos", fase1.getId());
        servicio.registrarPrueba(pasarela);
        servicio.registrarPrueba(fotos);

        Perfil perfil = new Perfil(new PerfilID(), "Cundinamarca", Sexo.FEMENINO, new RangoAltura(1.70, 1.85),
                new RangoEdad(18, 30), "Castano", "Cafe", Especialidad.MODELO, "2 años en pasarela");
        servicio.registrarPerfil(perfil);

        Representante representante = new Representante(new RepresentanteID(), "Carlos Ruiz", "3109876543");
        servicio.registrarRepresentante(representante);

        Candidato ana = new Candidato(new CandidatoID(), new CodigoCandidato("CAN-001"), "Ana Torres",
                "Cra 7 # 12-40", "3151112233", LocalDate.of(2000, 5, 14), "ana.jpg", null);
        Candidato pedro = new Candidato(new CandidatoID(), new CodigoCandidato("CAN-002"), "Pedro Diaz",
                "Av 3 # 8-15", "3164445566", LocalDate.of(1998, 11, 2), "pedro.jpg", null);
        Candidato sofia = new Candidato(new CandidatoID(), new CodigoCandidato("CAN-003"), "Sofia Lopez",
                "Cll 45 # 20-10", "3177778899", LocalDate.now().minusYears(15), "sofia.jpg", "Marta Lopez");
        servicio.registrarCandidato(ana);
        servicio.registrarCandidato(pedro);
        servicio.registrarCandidato(sofia);
        servicio.asignarPerfil(ana.getId(), perfil.getId());
        servicio.asignarRepresentante(ana.getId(), representante.getId());

        servicio.registrarAgente(new AgenteCasting(new AgenteCastingID(), "EMP-01", new DNI("1020304050"),
                "Julian Mora", "Cll 80 # 15-30"));

        servicio.registrarResultado(new Resultado(new ResultadoID(), ana.getId(), pasarela.getId(),
                EstadoResultado.APROBADO));
        servicio.registrarResultado(new Resultado(new ResultadoID(), ana.getId(), fotos.getId(),
                EstadoResultado.APROBADO));
        servicio.registrarResultado(new Resultado(new ResultadoID(), pedro.getId(), pasarela.getId(),
                EstadoResultado.NO_APROBADO));
    }
}
