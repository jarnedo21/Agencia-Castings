package co.edu.udec.agenciacastings.domain.model;

import co.edu.udec.agenciacastings.domain.exceptions.PruebaException;
import co.edu.udec.agenciacastings.domain.valueobjects.FaseID;
import co.edu.udec.agenciacastings.domain.valueobjects.PruebaID;

import java.time.LocalDate;

public class Prueba {
    private final PruebaID id;
    private final int numero;
    private final LocalDate fecha;
    private final String sala;
    private final String descripcion;
    private final FaseID faseId;

    public Prueba(PruebaID id, int numero, LocalDate fecha, String sala, String descripcion, FaseID faseId) {
        if (faseId == null) {
            throw new PruebaException("Una prueba debe estar asociada a una fase");
        }
        if (id == null) throw new PruebaException("El id no puede ser nulo");
        if (numero <= 0) throw new PruebaException("El numero de la prueba debe ser mayor que cero");

        this.id = id;
        this.numero = numero;
        this.fecha = fecha;
        this.sala = sala;
        this.descripcion = descripcion;
        this.faseId = faseId;
    }

    public PruebaID getId() {
        return id;
    }

    public int getNumero() {
        return numero;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getSala() {
        return sala;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public FaseID getFaseId() {
        return faseId;
    }

    @Override
    public String toString() {
        return "Prueba[id=" + id + ", numero=" + numero + ", fecha=" + fecha + ", sala=" + sala
                + ", descripcion=" + descripcion + ", fase=" + faseId + "]";
    }
}
