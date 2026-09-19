package co.edu.udec.agenciacastings.domain.model;

import co.edu.udec.agenciacastings.domain.enums.TipoActividad;
import co.edu.udec.agenciacastings.domain.exceptions.ClienteException;
import co.edu.udec.agenciacastings.domain.valueobjects.ClienteID;
import co.edu.udec.agenciacastings.domain.valueobjects.CodigoCliente;

public class Cliente {
    private final ClienteID id;
    private final CodigoCliente codigo;
    private final String nombre;
    private String direccion;
    private String telefono;
    private String contacto;
    private final TipoActividad tipoActividad;

    public Cliente(ClienteID id, CodigoCliente codigo, String nombre, String direccion, String telefono,
                   String contacto, TipoActividad tipoActividad) {
        if (id == null) throw new ClienteException("El id no puede ser nulo");
        if (codigo == null) throw new ClienteException("El codigo no puede ser nulo");
        if (nombre == null || nombre.isBlank()) throw new ClienteException("El nombre no puede estar vacio");
        if (tipoActividad == null) throw new ClienteException("El tipo de actividad no puede ser nulo");

        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.contacto = contacto;
        this.tipoActividad = tipoActividad;
    }

    public void cambiarDireccion(String nuevaDireccion) {
        if (nuevaDireccion == null || nuevaDireccion.isBlank()) {
            throw new ClienteException("La direccion no puede estar vacia");
        }
        this.direccion = nuevaDireccion;
    }

    public void cambiarTelefono(String nuevoTelefono) {
        if (nuevoTelefono == null || nuevoTelefono.isBlank()) {
            throw new ClienteException("El telefono no puede estar vacio");
        }
        this.telefono = nuevoTelefono;
    }

    public void cambiarContacto(String nuevoContacto) {
        if (nuevoContacto == null || nuevoContacto.isBlank()) {
            throw new ClienteException("El contacto no puede estar vacio");
        }
        this.contacto = nuevoContacto;
    }

    public ClienteID getId() {
        return id;
    }

    public CodigoCliente getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getContacto() {
        return contacto;
    }

    public TipoActividad getTipoActividad() {
        return tipoActividad;
    }

    @Override
    public String toString() {
        return "Cliente[id=" + id + ", codigo=" + codigo + ", nombre=" + nombre + ", telefono=" + telefono
                + ", contacto=" + contacto + ", actividad=" + tipoActividad + "]";
    }
}
