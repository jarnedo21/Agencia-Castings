package co.edu.udec.agenciacastings.domain.model;

import co.edu.udec.agenciacastings.domain.exceptions.CandidatoException;
import co.edu.udec.agenciacastings.domain.exceptions.CandidatoMenorSinTutorException;
import co.edu.udec.agenciacastings.domain.valueobjects.CandidatoID;
import co.edu.udec.agenciacastings.domain.valueobjects.CodigoCandidato;
import co.edu.udec.agenciacastings.domain.valueobjects.PerfilID;
import co.edu.udec.agenciacastings.domain.valueobjects.RepresentanteID;

import java.time.LocalDate;
import java.time.Period;

public class Candidato {
    private final CandidatoID id;
    private final CodigoCandidato codigo;
    private final String nombre;
    private String direccion;
    private String telefono;
    private final LocalDate fechaNacimiento;
    private String fotografia;
    private String tutor;
    private PerfilID perfilId;
    private RepresentanteID representanteId;

    public Candidato(CandidatoID id, CodigoCandidato codigo, String nombre, String direccion, String telefono,
                     LocalDate fechaNacimiento, String fotografia, String tutor) {
        if (id == null) throw new CandidatoException("El id no puede ser nulo");
        if (codigo == null) throw new CandidatoException("El codigo no puede ser nulo");
        if (nombre == null || nombre.isBlank()) throw new CandidatoException("El nombre no puede estar vacio");
        if (fechaNacimiento == null) throw new CandidatoException("La fecha de nacimiento no puede ser nula");
        if (fechaNacimiento.isAfter(LocalDate.now())) {
            throw new CandidatoException("La fecha de nacimiento no puede ser futura");
        }

        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.fotografia = fotografia;
        this.tutor = tutor;

        if (esMenorDeEdad() && (tutor == null || tutor.isBlank())) {
            throw new CandidatoMenorSinTutorException("Debe registrar el nombre del tutor para"
                    + " candidatos menores de edad");
        }
    }

    public int getEdad() {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    public boolean esMenorDeEdad() {
        return getEdad() < 18;
    }

    public void cambiarDireccion(String nuevaDireccion) {
        if (nuevaDireccion == null || nuevaDireccion.isBlank()) {
            throw new CandidatoException("La direccion no puede estar vacia");
        }
        this.direccion = nuevaDireccion;
    }

    public void cambiarTelefono(String nuevoTelefono) {
        if (nuevoTelefono == null || nuevoTelefono.isBlank()) {
            throw new CandidatoException("El telefono no puede estar vacio");
        }
        this.telefono = nuevoTelefono;
    }

    public void cambiarFotografia(String nuevaFotografia) {
        if (nuevaFotografia == null || nuevaFotografia.isBlank()) {
            throw new CandidatoException("La fotografia no puede estar vacia");
        }
        this.fotografia = nuevaFotografia;
    }

    public void cambiarTutor(String nuevoTutor) {
        if (esMenorDeEdad() && (nuevoTutor == null || nuevoTutor.isBlank())) {
            throw new CandidatoMenorSinTutorException("Un candidato menor de edad no puede quedar sin tutor");
        }
        this.tutor = nuevoTutor;
    }

    public void asignarPerfil(PerfilID perfilId) {
        if (perfilId == null) throw new CandidatoException("El perfil no puede ser nulo");
        this.perfilId = perfilId;
    }

    public void asignarRepresentante(RepresentanteID representanteId) {
        if (representanteId == null) throw new CandidatoException("El representante no puede ser nulo");
        this.representanteId = representanteId;
    }

    public CandidatoID getId() {
        return id;
    }

    public CodigoCandidato getCodigo() {
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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getFotografia() {
        return fotografia;
    }

    public String getTutor() {
        return tutor;
    }

    public PerfilID getPerfilId() {
        return perfilId;
    }

    public RepresentanteID getRepresentanteId() {
        return representanteId;
    }

    @Override
    public String toString() {
        return "Candidato[id=" + id + ", codigo=" + codigo + ", nombre=" + nombre + ", edad=" + getEdad()
                + ", telefono=" + telefono + ", tutor=" + (tutor == null ? "-" : tutor)
                + ", perfil=" + (perfilId == null ? "-" : perfilId)
                + ", representante=" + (representanteId == null ? "-" : representanteId) + "]";
    }
}
