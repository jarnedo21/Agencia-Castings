package co.edu.udec.agenciacastings.domain.model;

import co.edu.udec.agenciacastings.domain.enums.Especialidad;
import co.edu.udec.agenciacastings.domain.enums.Sexo;
import co.edu.udec.agenciacastings.domain.exceptions.PerfilException;
import co.edu.udec.agenciacastings.domain.valueobjects.PerfilID;
import co.edu.udec.agenciacastings.domain.valueobjects.RangoAltura;
import co.edu.udec.agenciacastings.domain.valueobjects.RangoEdad;

public class Perfil {
    private final PerfilID id;
    private final String provincia;
    private final Sexo sexo;
    private final RangoAltura rangoAltura;
    private final RangoEdad rangoEdad;
    private final String colorPelo;
    private final String colorOjos;
    private final Especialidad especialidad;
    private String experiencia;

    public Perfil(PerfilID id, String provincia, Sexo sexo, RangoAltura rangoAltura, RangoEdad rangoEdad,
                  String colorPelo, String colorOjos, Especialidad especialidad, String experiencia) {
        if (id == null) throw new PerfilException("El id no puede ser nulo");
        if (especialidad == null) throw new PerfilException("La especialidad no puede ser nula");
        if (sexo == null) throw new PerfilException("El sexo no puede ser nulo");

        this.id = id;
        this.provincia = provincia;
        this.sexo = sexo;
        this.rangoAltura = rangoAltura;
        this.rangoEdad = rangoEdad;
        this.colorPelo = colorPelo;
        this.colorOjos = colorOjos;
        this.especialidad = especialidad;
        this.experiencia = experiencia;
    }

    public void cambiarExperiencia(String nuevaExperiencia) {
        this.experiencia = nuevaExperiencia;
    }

    public PerfilID getId() {
        return id;
    }

    public String getProvincia() {
        return provincia;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public RangoAltura getRangoAltura() {
        return rangoAltura;
    }

    public RangoEdad getRangoEdad() {
        return rangoEdad;
    }

    public String getColorPelo() {
        return colorPelo;
    }

    public String getColorOjos() {
        return colorOjos;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public String getExperiencia() {
        return experiencia;
    }

    @Override
    public String toString() {
        return "Perfil[id=" + id + ", provincia=" + provincia + ", sexo=" + sexo + ", altura=" + rangoAltura
                + ", edad=" + rangoEdad + ", pelo=" + colorPelo + ", ojos=" + colorOjos
                + ", especialidad=" + especialidad + ", experiencia=" + experiencia + "]";
    }
}
