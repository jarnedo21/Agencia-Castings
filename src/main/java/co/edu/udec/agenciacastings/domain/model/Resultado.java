package co.edu.udec.agenciacastings.domain.model;

import co.edu.udec.agenciacastings.domain.enums.EstadoResultado;
import co.edu.udec.agenciacastings.domain.exceptions.ResultadoException;
import co.edu.udec.agenciacastings.domain.valueobjects.CandidatoID;
import co.edu.udec.agenciacastings.domain.valueobjects.PruebaID;
import co.edu.udec.agenciacastings.domain.valueobjects.ResultadoID;

public class Resultado {
    private final ResultadoID id;
    private final CandidatoID candidatoId;
    private final PruebaID pruebaId;
    private final EstadoResultado estado;

    public Resultado(ResultadoID id, CandidatoID candidatoId, PruebaID pruebaId, EstadoResultado estado) {
        if (candidatoId == null || pruebaId == null) {
            throw new ResultadoException("Un resultado debe estar asociado a un candidato y una prueba");
        }
        if (id == null) throw new ResultadoException("El id no puede ser nulo");
        if (estado == null) throw new ResultadoException("El estado no puede ser nulo");

        this.id = id;
        this.candidatoId = candidatoId;
        this.pruebaId = pruebaId;
        this.estado = estado;
    }

    public boolean fueAprobado() {
        return this.estado == EstadoResultado.APROBADO;
    }

    public ResultadoID getId() {
        return id;
    }

    public CandidatoID getCandidatoId() {
        return candidatoId;
    }

    public PruebaID getPruebaId() {
        return pruebaId;
    }

    public EstadoResultado getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "Resultado[id=" + id + ", candidato=" + candidatoId + ", prueba=" + pruebaId
                + ", estado=" + estado + "]";
    }
}
