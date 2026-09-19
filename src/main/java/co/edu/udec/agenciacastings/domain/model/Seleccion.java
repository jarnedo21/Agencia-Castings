package co.edu.udec.agenciacastings.domain.model;

import co.edu.udec.agenciacastings.domain.exceptions.SeleccionException;
import co.edu.udec.agenciacastings.domain.valueobjects.CandidatoID;
import co.edu.udec.agenciacastings.domain.valueobjects.CastingID;
import co.edu.udec.agenciacastings.domain.valueobjects.SeleccionID;

import java.time.LocalDate;

public class Seleccion {
    private final SeleccionID id;
    private final CandidatoID candidatoId;
    private final CastingID castingId;
    private final LocalDate fechaSeleccion;

    public Seleccion(SeleccionID id, CandidatoID candidatoId, CastingID castingId, LocalDate fechaSeleccion) {
        if (candidatoId == null || castingId == null) {
            throw new SeleccionException("Una seleccion debe estar asociada a un candidato y un casting");
        }
        if (id == null) throw new SeleccionException("El id no puede ser nulo");

        this.id = id;
        this.candidatoId = candidatoId;
        this.castingId = castingId;
        this.fechaSeleccion = fechaSeleccion;
    }

    public SeleccionID getId() {
        return id;
    }

    public CandidatoID getCandidatoId() {
        return candidatoId;
    }

    public CastingID getCastingId() {
        return castingId;
    }

    public LocalDate getFechaSeleccion() {
        return fechaSeleccion;
    }

    @Override
    public String toString() {
        return "Seleccion[id=" + id + ", candidato=" + candidatoId + ", casting=" + castingId
                + ", fecha=" + fechaSeleccion + "]";
    }
}
