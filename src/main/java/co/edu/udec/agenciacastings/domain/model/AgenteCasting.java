package co.edu.udec.agenciacastings.domain.model;

import co.edu.udec.agenciacastings.domain.exceptions.AgenteCastingException;
import co.edu.udec.agenciacastings.domain.valueobjects.AgenteCastingID;
import co.edu.udec.agenciacastings.domain.valueobjects.DNI;

public class AgenteCasting {
    private final AgenteCastingID id;
    private final String numeroEmpleado;
    private final DNI dni;
    private final String nombre;
    private String direccion;

    public AgenteCasting(AgenteCastingID id, String numeroEmpleado, DNI dni, String nombre, String direccion) {
        if (id == null) throw new AgenteCastingException("El id no puede ser nulo");
        if (dni == null) throw new AgenteCastingException("El DNI no puede ser nulo");
        if (numeroEmpleado == null || numeroEmpleado.isBlank()) {
            throw new AgenteCastingException("El numero de empleado no puede estar vacio");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new AgenteCastingException("El nombre no puede estar vacio");
        }

        this.id = id;
        this.numeroEmpleado = numeroEmpleado;
        this.dni = dni;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public void cambiarDireccion(String nuevaDireccion) {
        if (nuevaDireccion == null || nuevaDireccion.isBlank()) {
            throw new AgenteCastingException("La direccion no puede estar vacia");
        }
        this.direccion = nuevaDireccion;
    }

    public AgenteCastingID getId() {
        return id;
    }

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public DNI getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    @Override
    public String toString() {
        return "AgenteCasting[id=" + id + ", empleado=" + numeroEmpleado + ", dni=" + dni
                + ", nombre=" + nombre + ", direccion=" + direccion + "]";
    }
}
