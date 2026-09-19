package co.edu.udec.agenciacastings.application;

import co.edu.udec.agenciacastings.application.exceptions.EntidadNoEncontradaException;
import co.edu.udec.agenciacastings.application.exceptions.RegistroDuplicadoException;
import co.edu.udec.agenciacastings.domain.exceptions.SeleccionException;
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
import co.edu.udec.agenciacastings.domain.repository.Repositorio;
import co.edu.udec.agenciacastings.domain.valueobjects.AgenteCastingID;
import co.edu.udec.agenciacastings.domain.valueobjects.CandidatoID;
import co.edu.udec.agenciacastings.domain.valueobjects.CastingID;
import co.edu.udec.agenciacastings.domain.valueobjects.ClienteID;
import co.edu.udec.agenciacastings.domain.valueobjects.FaseID;
import co.edu.udec.agenciacastings.domain.valueobjects.PerfilID;
import co.edu.udec.agenciacastings.domain.valueobjects.PruebaID;
import co.edu.udec.agenciacastings.domain.valueobjects.RepresentanteID;
import co.edu.udec.agenciacastings.domain.valueobjects.ResultadoID;
import co.edu.udec.agenciacastings.domain.valueobjects.SeleccionID;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Casos de uso de la agencia: registra las entidades verificando que las referencias
 * existan y que se cumplan las reglas de negocio que involucran a varias entidades.
 */
public class AgenciaCastingService {
    private final Repositorio<Cliente, ClienteID> clientes;
    private final Repositorio<Casting, CastingID> castings;
    private final Repositorio<Fase, FaseID> fases;
    private final Repositorio<Prueba, PruebaID> pruebas;
    private final Repositorio<Candidato, CandidatoID> candidatos;
    private final Repositorio<Perfil, PerfilID> perfiles;
    private final Repositorio<Representante, RepresentanteID> representantes;
    private final Repositorio<AgenteCasting, AgenteCastingID> agentes;
    private final Repositorio<Resultado, ResultadoID> resultados;
    private final Repositorio<Seleccion, SeleccionID> selecciones;

    public AgenciaCastingService(Repositorio<Cliente, ClienteID> clientes,
                                 Repositorio<Casting, CastingID> castings,
                                 Repositorio<Fase, FaseID> fases,
                                 Repositorio<Prueba, PruebaID> pruebas,
                                 Repositorio<Candidato, CandidatoID> candidatos,
                                 Repositorio<Perfil, PerfilID> perfiles,
                                 Repositorio<Representante, RepresentanteID> representantes,
                                 Repositorio<AgenteCasting, AgenteCastingID> agentes,
                                 Repositorio<Resultado, ResultadoID> resultados,
                                 Repositorio<Seleccion, SeleccionID> selecciones) {
        this.clientes = clientes;
        this.castings = castings;
        this.fases = fases;
        this.pruebas = pruebas;
        this.candidatos = candidatos;
        this.perfiles = perfiles;
        this.representantes = representantes;
        this.agentes = agentes;
        this.resultados = resultados;
        this.selecciones = selecciones;
    }

    // ---------------------------------------------------------------- registros

    public void registrarCliente(Cliente cliente) {
        if (!clientes.buscar(c -> c.getCodigo().equals(cliente.getCodigo())).isEmpty()) {
            throw new RegistroDuplicadoException("Ya existe un cliente con codigo " + cliente.getCodigo());
        }
        clientes.guardar(cliente);
    }

    public void registrarCasting(Casting casting) {
        obtenerCliente(casting.getClienteId());
        if (!castings.buscar(c -> c.getCodigo().equals(casting.getCodigo())).isEmpty()) {
            throw new RegistroDuplicadoException("Ya existe un casting con codigo " + casting.getCodigo());
        }
        castings.guardar(casting);
    }

    public void registrarFase(Fase fase) {
        obtenerCasting(fase.getCastingId());
        boolean repetida = fasesDeCasting(fase.getCastingId()).stream()
                .anyMatch(f -> f.getNumeroIdentificacion() == fase.getNumeroIdentificacion());
        if (repetida) {
            throw new RegistroDuplicadoException("El casting ya tiene una fase numero " + fase.getNumeroIdentificacion());
        }
        fases.guardar(fase);
    }

    public void registrarPrueba(Prueba prueba) {
        obtenerFase(prueba.getFaseId());
        boolean repetida = pruebasDeFase(prueba.getFaseId()).stream()
                .anyMatch(p -> p.getNumero() == prueba.getNumero());
        if (repetida) {
            throw new RegistroDuplicadoException("La fase ya tiene una prueba numero " + prueba.getNumero());
        }
        pruebas.guardar(prueba);
    }

    public void registrarCandidato(Candidato candidato) {
        if (!candidatos.buscar(c -> c.getCodigo().equals(candidato.getCodigo())).isEmpty()) {
            throw new RegistroDuplicadoException("Ya existe un candidato con codigo " + candidato.getCodigo());
        }
        candidatos.guardar(candidato);
    }

    public void registrarPerfil(Perfil perfil) {
        perfiles.guardar(perfil);
    }

    public void registrarRepresentante(Representante representante) {
        representantes.guardar(representante);
    }

    public void registrarAgente(AgenteCasting agente) {
        if (!agentes.buscar(a -> a.getDni().equals(agente.getDni())).isEmpty()) {
            throw new RegistroDuplicadoException("Ya existe un agente con DNI " + agente.getDni());
        }
        agentes.guardar(agente);
    }

    public void asignarPerfil(CandidatoID candidatoId, PerfilID perfilId) {
        Candidato candidato = obtenerCandidato(candidatoId);
        if (!perfiles.existe(perfilId)) throw new EntidadNoEncontradaException("No existe el perfil " + perfilId);
        candidato.asignarPerfil(perfilId);
        candidatos.guardar(candidato);
    }

    public void asignarRepresentante(CandidatoID candidatoId, RepresentanteID representanteId) {
        Candidato candidato = obtenerCandidato(candidatoId);
        if (!representantes.existe(representanteId)) {
            throw new EntidadNoEncontradaException("No existe el representante " + representanteId);
        }
        candidato.asignarRepresentante(representanteId);
        candidatos.guardar(candidato);
    }

    public void registrarResultado(Resultado resultado) {
        obtenerCandidato(resultado.getCandidatoId());
        obtenerPrueba(resultado.getPruebaId());
        boolean repetido = !resultados.buscar(r -> r.getCandidatoId().equals(resultado.getCandidatoId())
                && r.getPruebaId().equals(resultado.getPruebaId())).isEmpty();
        if (repetido) {
            throw new RegistroDuplicadoException("El candidato ya tiene un resultado para esa prueba");
        }
        resultados.guardar(resultado);
    }

    /**
     * Reglas: el candidato debe haber aprobado al menos una prueba del casting y no haber
     * reprobado ninguna; no puede ser seleccionado dos veces y no se supera el cupo del casting.
     */
    public void registrarSeleccion(Seleccion seleccion) {
        Casting casting = obtenerCasting(seleccion.getCastingId());
        obtenerCandidato(seleccion.getCandidatoId());

        List<Seleccion> yaSeleccionados = seleccionesDeCasting(casting.getId());
        if (yaSeleccionados.stream().anyMatch(s -> s.getCandidatoId().equals(seleccion.getCandidatoId()))) {
            throw new SeleccionException("El candidato ya fue seleccionado para este casting");
        }
        if (yaSeleccionados.size() >= casting.getNumeroPersonasRequeridas()) {
            throw new SeleccionException("El casting ya completo las " + casting.getNumeroPersonasRequeridas()
                    + " personas requeridas");
        }

        List<Resultado> resultadosEnCasting = resultadosDeCandidatoEnCasting(seleccion.getCandidatoId(), casting.getId());
        if (resultadosEnCasting.isEmpty()) {
            throw new SeleccionException("El candidato no tiene resultados en las pruebas de este casting");
        }
        if (resultadosEnCasting.stream().anyMatch(r -> !r.fueAprobado())) {
            throw new SeleccionException("El candidato no aprobo todas sus pruebas en este casting");
        }
        selecciones.guardar(seleccion);
    }

    // ---------------------------------------------------------------- consultas

    public List<Cliente> listarClientes() {
        return clientes.listar();
    }

    public List<Casting> listarCastings() {
        return castings.listar();
    }

    public List<Fase> listarFases() {
        return fases.listar();
    }

    public List<Prueba> listarPruebas() {
        return pruebas.listar();
    }

    public List<Candidato> listarCandidatos() {
        return candidatos.listar();
    }

    public List<Perfil> listarPerfiles() {
        return perfiles.listar();
    }

    public List<Representante> listarRepresentantes() {
        return representantes.listar();
    }

    public List<AgenteCasting> listarAgentes() {
        return agentes.listar();
    }

    public List<Resultado> listarResultados() {
        return resultados.listar();
    }

    public List<Seleccion> listarSelecciones() {
        return selecciones.listar();
    }

    public List<Casting> castingsDeCliente(ClienteID clienteId) {
        return castings.buscar(c -> c.getClienteId().equals(clienteId));
    }

    public List<Fase> fasesDeCasting(CastingID castingId) {
        return fases.buscar(f -> f.getCastingId().equals(castingId));
    }

    public List<Prueba> pruebasDeFase(FaseID faseId) {
        return pruebas.buscar(p -> p.getFaseId().equals(faseId));
    }

    public List<Prueba> pruebasDeCasting(CastingID castingId) {
        Set<FaseID> idsFases = fasesDeCasting(castingId).stream().map(Fase::getId).collect(Collectors.toSet());
        return pruebas.buscar(p -> idsFases.contains(p.getFaseId()));
    }

    public List<Resultado> resultadosDeCandidato(CandidatoID candidatoId) {
        return resultados.buscar(r -> r.getCandidatoId().equals(candidatoId));
    }

    public List<Resultado> resultadosDeCandidatoEnCasting(CandidatoID candidatoId, CastingID castingId) {
        Set<PruebaID> idsPruebas = pruebasDeCasting(castingId).stream().map(Prueba::getId).collect(Collectors.toSet());
        return resultados.buscar(r -> r.getCandidatoId().equals(candidatoId) && idsPruebas.contains(r.getPruebaId()));
    }

    public List<Seleccion> seleccionesDeCasting(CastingID castingId) {
        return selecciones.buscar(s -> s.getCastingId().equals(castingId));
    }

    public List<Candidato> candidatosSeleccionados(CastingID castingId) {
        return seleccionesDeCasting(castingId).stream()
                .map(s -> obtenerCandidato(s.getCandidatoId()))
                .toList();
    }

    public List<Candidato> candidatosMenoresDeEdad() {
        return candidatos.buscar(Candidato::esMenorDeEdad);
    }

    public Cliente obtenerCliente(ClienteID id) {
        return clientes.buscarPorId(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("No existe el cliente " + id));
    }

    public Casting obtenerCasting(CastingID id) {
        return castings.buscarPorId(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("No existe el casting " + id));
    }

    public Fase obtenerFase(FaseID id) {
        return fases.buscarPorId(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("No existe la fase " + id));
    }

    public Prueba obtenerPrueba(PruebaID id) {
        return pruebas.buscarPorId(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("No existe la prueba " + id));
    }

    public Candidato obtenerCandidato(CandidatoID id) {
        return candidatos.buscarPorId(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("No existe el candidato " + id));
    }
}
