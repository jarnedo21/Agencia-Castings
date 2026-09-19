package co.edu.udec.agenciacastings.domain.model;

import co.edu.udec.agenciacastings.domain.exceptions.RepresentanteException;
import co.edu.udec.agenciacastings.domain.valueobjects.RepresentanteID;

public class Representante {
    private final RepresentanteID id;
    private final String nombre;
    private String telefono;

    public Representante(RepresentanteID id, String nombre, String telefono) {
        if (id == null) throw new RepresentanteException("El id no puede ser nulo");
        if (nombre == null || nombre.isBlank()) {
            throw new RepresentanteException("El nombre no puede estar vacio");
        }

        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public void cambiarTelefono(String nuevoTelefono) {
        if (nuevoTelefono == null || nuevoTelefono.isBlank()) {
            throw new RepresentanteException("El telefono no puede estar vacio");
        }
        this.telefono = nuevoTelefono;
    }

    public RepresentanteID getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    @Override
    public String toString() {
        return "Representante[id=" + id + ", nombre=" + nombre + ", telefono=" + telefono + "]";
    }
}
