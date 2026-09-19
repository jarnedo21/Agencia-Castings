package co.edu.udec.agenciacastings.infrastructure.ui;

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
import co.edu.udec.agenciacastings.domain.model.Seleccion;
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
import co.edu.udec.agenciacastings.domain.valueobjects.SeleccionID;
import co.edu.udec.agenciacastings.infrastructure.config.DatosDeEjemplo;

import java.time.LocalDate;
import java.util.List;

/**
 * Menu de consola para ingresar datos y verificar las reglas del dominio.
 */
public class MenuPrincipal {
    private final AgenciaCastingService servicio;
    private final Consola consola;

    public MenuPrincipal(AgenciaCastingService servicio, Consola consola) {
        this.servicio = servicio;
        this.consola = consola;
    }

    public void iniciar() {
        consola.mostrar("=========================================");
        consola.mostrar("        AGENCIA DE CASTINGS - UdeC        ");
        consola.mostrar("=========================================");
        try {
            boolean continuar = true;
            while (continuar) {
                mostrarOpciones();
                int opcion = consola.leerEntero("Opcion");
                try {
                    continuar = ejecutar(opcion);
                } catch (Consola.FinDeEntradaException e) {
                    throw e;
                } catch (RuntimeException e) {
                    // Aqui se ven las validaciones del dominio y de la aplicacion
                    consola.mostrar("  X Error (" + e.getClass().getSimpleName() + "): " + e.getMessage());
                }
            }
        } catch (Consola.FinDeEntradaException e) {
            consola.mostrar("");
        }
        consola.mostrar("Hasta luego.");
    }

    private void mostrarOpciones() {
        consola.mostrar("");
        consola.mostrar("--- REGISTRAR ---------------------------");
        consola.mostrar("  1. Cliente");
        consola.mostrar("  2. Casting");
        consola.mostrar("  3. Fase de un casting");
        consola.mostrar("  4. Prueba de una fase");
        consola.mostrar("  5. Candidato");
        consola.mostrar("  6. Perfil");
        consola.mostrar("  7. Representante");
        consola.mostrar("  8. Agente de casting");
        consola.mostrar("--- PROCESO -----------------------------");
        consola.mostrar("  9. Asignar perfil a candidato");
        consola.mostrar(" 10. Asignar representante a candidato");
        consola.mostrar(" 11. Registrar resultado de una prueba");
        consola.mostrar(" 12. Seleccionar candidato para un casting");
        consola.mostrar("--- VERIFICAR ---------------------------");
        consola.mostrar(" 13. Listar registros");
        consola.mostrar(" 14. Detalle de un casting");
        consola.mostrar(" 15. Resultados de un candidato");
        consola.mostrar(" 16. Candidatos menores de edad");
        consola.mostrar("-----------------------------------------");
        consola.mostrar(" 99. Cargar datos de ejemplo");
        consola.mostrar("  0. Salir");
    }

    private boolean ejecutar(int opcion) {
        switch (opcion) {
            case 1 -> registrarCliente();
            case 2 -> registrarCasting();
            case 3 -> registrarFase();
            case 4 -> registrarPrueba();
            case 5 -> registrarCandidato();
            case 6 -> registrarPerfil();
            case 7 -> registrarRepresentante();
            case 8 -> registrarAgente();
            case 9 -> asignarPerfil();
            case 10 -> asignarRepresentante();
            case 11 -> registrarResultado();
            case 12 -> seleccionarCandidato();
            case 13 -> listarRegistros();
            case 14 -> detalleCasting();
            case 15 -> resultadosCandidato();
            case 16 -> imprimir("Candidatos menores de edad", servicio.candidatosMenoresDeEdad());
            case 99 -> {
                DatosDeEjemplo.cargar(servicio);
                consola.mostrar("  OK Datos de ejemplo cargados");
            }
            case 0 -> {
                return false;
            }
            default -> consola.mostrar("  ! Opcion no valida");
        }
        return true;
    }

    // ---------------------------------------------------------------- registros

    private void registrarCliente() {
        Cliente cliente = new Cliente(
                new ClienteID(),
                new CodigoCliente(consola.leerTexto("Codigo")),
                consola.leerTexto("Nombre"),
                consola.leerTexto("Direccion"),
                consola.leerTexto("Telefono"),
                consola.leerTexto("Persona de contacto"),
                consola.leerEnum("Tipo de actividad", TipoActividad.class));
        servicio.registrarCliente(cliente);
        ok(cliente);
    }

    private void registrarCasting() {
        Cliente cliente = consola.seleccionar("el cliente", servicio.listarClientes(),
                c -> c.getCodigo() + " - " + c.getNombre());
        if (cliente == null) return;

        Casting casting = new Casting(
                new CastingID(),
                new CodigoCasting(consola.leerTexto("Codigo")),
                consola.leerTexto("Nombre"),
                consola.leerTexto("Descripcion"),
                consola.leerFecha("Fecha de contratacion"),
                new Costo(consola.leerDecimal("Costo")),
                consola.leerEnum("Tipo de casting", TipoCasting.class),
                cliente.getId(),
                consola.leerEntero("Numero de personas requeridas"));
        servicio.registrarCasting(casting);
        ok(casting);
    }

    private void registrarFase() {
        Casting casting = elegirCasting();
        if (casting == null) return;

        Fase fase = new Fase(
                new FaseID(),
                consola.leerEntero("Numero de la fase"),
                consola.leerFecha("Fecha de inicio"),
                casting.getId());
        servicio.registrarFase(fase);
        ok(fase);
    }

    private void registrarPrueba() {
        Fase fase = elegirFase();
        if (fase == null) return;

        Prueba prueba = new Prueba(
                new PruebaID(),
                consola.leerEntero("Numero de la prueba"),
                consola.leerFecha("Fecha"),
                consola.leerTexto("Sala"),
                consola.leerTexto("Descripcion"),
                fase.getId());
        servicio.registrarPrueba(prueba);
        ok(prueba);
    }

    private void registrarCandidato() {
        CodigoCandidato codigo = new CodigoCandidato(consola.leerTexto("Codigo"));
        String nombre = consola.leerTexto("Nombre");
        String direccion = consola.leerTexto("Direccion");
        String telefono = consola.leerTexto("Telefono");
        LocalDate fechaNacimiento = consola.leerFecha("Fecha de nacimiento");
        String fotografia = consola.leerTextoOpcional("Fotografia (ruta o URL)");
        String tutor = consola.leerTextoOpcional("Tutor (obligatorio si es menor de edad)");

        Candidato candidato = new Candidato(new CandidatoID(), codigo, nombre, direccion, telefono,
                fechaNacimiento, fotografia, tutor);
        servicio.registrarCandidato(candidato);
        ok(candidato);
    }

    private void registrarPerfil() {
        Perfil perfil = new Perfil(
                new PerfilID(),
                consola.leerTexto("Provincia"),
                consola.leerEnum("Sexo", Sexo.class),
                new RangoAltura(consola.leerDecimal("Altura minima (m)"), consola.leerDecimal("Altura maxima (m)")),
                new RangoEdad(consola.leerEntero("Edad minima"), consola.leerEntero("Edad maxima")),
                consola.leerTexto("Color de pelo"),
                consola.leerTexto("Color de ojos"),
                consola.leerEnum("Especialidad", Especialidad.class),
                consola.leerTextoOpcional("Experiencia"));
        servicio.registrarPerfil(perfil);
        ok(perfil);
    }

    private void registrarRepresentante() {
        Representante representante = new Representante(
                new RepresentanteID(),
                consola.leerTexto("Nombre"),
                consola.leerTexto("Telefono"));
        servicio.registrarRepresentante(representante);
        ok(representante);
    }

    private void registrarAgente() {
        AgenteCasting agente = new AgenteCasting(
                new AgenteCastingID(),
                consola.leerTexto("Numero de empleado"),
                new DNI(consola.leerTexto("DNI")),
                consola.leerTexto("Nombre"),
                consola.leerTexto("Direccion"));
        servicio.registrarAgente(agente);
        ok(agente);
    }

    // ---------------------------------------------------------------- proceso

    private void asignarPerfil() {
        Candidato candidato = elegirCandidato();
        if (candidato == null) return;
        Perfil perfil = consola.seleccionar("el perfil", servicio.listarPerfiles(),
                p -> p.getEspecialidad() + ", " + p.getSexo() + ", " + p.getProvincia() + ", edad " + p.getRangoEdad());
        if (perfil == null) return;

        servicio.asignarPerfil(candidato.getId(), perfil.getId());
        ok(candidato);
    }

    private void asignarRepresentante() {
        Candidato candidato = elegirCandidato();
        if (candidato == null) return;
        Representante representante = consola.seleccionar("el representante", servicio.listarRepresentantes(),
                r -> r.getNombre() + " (" + r.getTelefono() + ")");
        if (representante == null) return;

        servicio.asignarRepresentante(candidato.getId(), representante.getId());
        ok(candidato);
    }

    private void registrarResultado() {
        Candidato candidato = elegirCandidato();
        if (candidato == null) return;
        Prueba prueba = consola.seleccionar("la prueba", servicio.listarPruebas(), this::describirPrueba);
        if (prueba == null) return;

        Resultado resultado = new Resultado(new ResultadoID(), candidato.getId(), prueba.getId(),
                consola.leerEnum("Estado", EstadoResultado.class));
        servicio.registrarResultado(resultado);
        ok(resultado);
    }

    private void seleccionarCandidato() {
        Casting casting = elegirCasting();
        if (casting == null) return;
        Candidato candidato = elegirCandidato();
        if (candidato == null) return;

        Seleccion seleccion = new Seleccion(new SeleccionID(), candidato.getId(), casting.getId(), LocalDate.now());
        servicio.registrarSeleccion(seleccion);
        ok(seleccion);
    }

    // ---------------------------------------------------------------- verificar

    private void listarRegistros() {
        imprimir("Clientes", servicio.listarClientes());
        imprimir("Castings", servicio.listarCastings());
        imprimir("Fases", servicio.listarFases());
        imprimir("Pruebas", servicio.listarPruebas());
        imprimir("Candidatos", servicio.listarCandidatos());
        imprimir("Perfiles", servicio.listarPerfiles());
        imprimir("Representantes", servicio.listarRepresentantes());
        imprimir("Agentes de casting", servicio.listarAgentes());
        imprimir("Resultados", servicio.listarResultados());
        imprimir("Selecciones", servicio.listarSelecciones());
    }

    private void detalleCasting() {
        Casting casting = elegirCasting();
        if (casting == null) return;

        Cliente cliente = servicio.obtenerCliente(casting.getClienteId());
        consola.mostrar("");
        consola.mostrar(casting.getNombre() + " [" + casting.getCodigo() + "] - " + casting.getTipo());
        consola.mostrar("  Cliente: " + cliente.getNombre() + " | Costo: " + casting.getCosto()
                + " | Cupos: " + servicio.seleccionesDeCasting(casting.getId()).size()
                + "/" + casting.getNumeroPersonasRequeridas());
        for (Fase fase : servicio.fasesDeCasting(casting.getId())) {
            consola.mostrar("  Fase " + fase.getNumeroIdentificacion() + " (inicio " + fase.getFechaInicio() + ")");
            for (Prueba prueba : servicio.pruebasDeFase(fase.getId())) {
                consola.mostrar("    Prueba " + prueba.getNumero() + ": " + prueba.getDescripcion()
                        + " - sala " + prueba.getSala());
            }
        }
        List<Candidato> seleccionados = servicio.candidatosSeleccionados(casting.getId());
        consola.mostrar("  Seleccionados: " + (seleccionados.isEmpty() ? "ninguno" : ""));
        seleccionados.forEach(c -> consola.mostrar("    - " + c.getNombre() + " (" + c.getCodigo() + ")"));
    }

    private void resultadosCandidato() {
        Candidato candidato = elegirCandidato();
        if (candidato == null) return;

        List<Resultado> resultados = servicio.resultadosDeCandidato(candidato.getId());
        consola.mostrar("");
        consola.mostrar("Resultados de " + candidato.getNombre() + ":");
        if (resultados.isEmpty()) consola.mostrar("  (sin resultados)");
        for (Resultado r : resultados) {
            Prueba prueba = servicio.obtenerPrueba(r.getPruebaId());
            consola.mostrar("  " + describirPrueba(prueba) + " -> " + r.getEstado());
        }
    }

    // ---------------------------------------------------------------- auxiliares

    private Casting elegirCasting() {
        return consola.seleccionar("el casting", servicio.listarCastings(),
                c -> c.getCodigo() + " - " + c.getNombre());
    }

    private Fase elegirFase() {
        return consola.seleccionar("la fase", servicio.listarFases(),
                f -> servicio.obtenerCasting(f.getCastingId()).getNombre() + " / fase " + f.getNumeroIdentificacion());
    }

    private Candidato elegirCandidato() {
        return consola.seleccionar("el candidato", servicio.listarCandidatos(),
                c -> c.getCodigo() + " - " + c.getNombre());
    }

    private String describirPrueba(Prueba prueba) {
        Fase fase = servicio.obtenerFase(prueba.getFaseId());
        Casting casting = servicio.obtenerCasting(fase.getCastingId());
        return casting.getNombre() + " / fase " + fase.getNumeroIdentificacion()
                + " / prueba " + prueba.getNumero() + " (" + prueba.getDescripcion() + ")";
    }

    private void imprimir(String titulo, List<?> elementos) {
        consola.mostrar("");
        consola.mostrar("== " + titulo + " (" + elementos.size() + ")");
        elementos.forEach(e -> consola.mostrar("  " + e));
    }

    private void ok(Object registro) {
        consola.mostrar("  OK Guardado: " + registro);
    }
}
