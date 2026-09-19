package co.edu.udec.agenciacastings.domain.model;

import co.edu.udec.agenciacastings.domain.exceptions.FaseException;
import co.edu.udec.agenciacastings.domain.valueobjects.CastingID;
import co.edu.udec.agenciacastings.domain.valueobjects.FaseID;

import java.time.LocalDate;

public class Fase {
    private final FaseID id;
    private final int numeroIdentificacion;
    private final LocalDate fechaInicio;
    private final CastingID castingId;

    public Fase(FaseID id, int numeroIdentificacion, LocalDate fechaInicio, CastingID castingId) {
        if (castingId == null) {
            throw new FaseException("Una fase debe estar asociada a un casting");
        }
        if (id == null) throw new FaseException("El id no puede ser nulo");
        if (numeroIdentificacion <= 0) throw new FaseException("El numero de la fase debe ser mayor que cero");

        this.id = id;
        this.numeroIdentificacion = numeroIdentificacion;
        this.fechaInicio = fechaInicio;
        this.castingId = castingId;
    }

    public FaseID getId() {
        return id;
    }

    public int getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public CastingID getCastingId() {
        return castingId;
    }

    @Override
    public String toString() {
        return "Fase[id=" + id + ", numero=" + numeroIdentificacion + ", inicio=" + fechaInicio
                + ", casting=" + castingId + "]";
    }
}
