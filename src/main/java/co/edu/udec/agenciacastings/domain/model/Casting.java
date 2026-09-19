package co.edu.udec.agenciacastings.domain.model;

import co.edu.udec.agenciacastings.domain.enums.TipoCasting;
import co.edu.udec.agenciacastings.domain.exceptions.CastingException;
import co.edu.udec.agenciacastings.domain.exceptions.CastingSinClienteException;
import co.edu.udec.agenciacastings.domain.valueobjects.CastingID;
import co.edu.udec.agenciacastings.domain.valueobjects.ClienteID;
import co.edu.udec.agenciacastings.domain.valueobjects.CodigoCasting;
import co.edu.udec.agenciacastings.domain.valueobjects.Costo;

import java.time.LocalDate;

public class Casting {
    private final CastingID id;
    private final CodigoCasting codigo;
    private final String nombre;
    private String descripcion;
    private final LocalDate fechaContratacion;
    private final Costo costo;
    private final TipoCasting tipo;
    private final ClienteID clienteId;
    private final int numeroPersonasRequeridas;

    public Casting(CastingID id, CodigoCasting codigo, String nombre, String descripcion, LocalDate fechaContratacion,
                   Costo costo, TipoCasting tipo, ClienteID clienteId, int numeroPersonasRequeridas) {
        if (clienteId == null) {
            throw new CastingSinClienteException("Un casting debe estar asociado a un cliente");
        }
        if (id == null) throw new CastingException("El id no puede ser nulo");
        if (codigo == null) throw new CastingException("El codigo no puede ser nulo");
        if (nombre == null || nombre.isBlank()) throw new CastingException("El nombre no puede estar vacio");
        if (tipo == null) throw new CastingException("El tipo de casting no puede ser nulo");
        if (numeroPersonasRequeridas <= 0) {
            throw new CastingException("El numero de personas requeridas debe ser mayor que cero");
        }

        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaContratacion = fechaContratacion;
        this.costo = costo;
        this.tipo = tipo;
        this.clienteId = clienteId;
        this.numeroPersonasRequeridas = numeroPersonasRequeridas;
    }

    public boolean esPresencial() {
        return this.tipo == TipoCasting.PRESENCIAL;
    }

    public void cambiarDescripcion(String nuevaDescripcion) {
        if (nuevaDescripcion == null || nuevaDescripcion.isBlank()) {
            throw new CastingException("La descripcion no puede estar vacia");
        }
        this.descripcion = nuevaDescripcion;
    }

    public CastingID getId() {
        return id;
    }

    public CodigoCasting getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public Costo getCosto() {
        return costo;
    }

    public TipoCasting getTipo() {
        return tipo;
    }

    public ClienteID getClienteId() {
        return clienteId;
    }

    public int getNumeroPersonasRequeridas() {
        return numeroPersonasRequeridas;
    }

    @Override
    public String toString() {
        return "Casting[id=" + id + ", codigo=" + codigo + ", nombre=" + nombre + ", tipo=" + tipo
                + ", fecha=" + fechaContratacion + ", costo=" + costo + ", cliente=" + clienteId
                + ", personas=" + numeroPersonasRequeridas + "]";
    }
}
