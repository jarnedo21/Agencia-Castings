package co.edu.udec.agenciacastings.domain.valueobjects;

public class RangoEdad {
    private final int edadMinima;
    private final int edadMaxima;

    public RangoEdad(int edadMinima, int edadMaxima) {
        if (edadMinima < 0 || edadMaxima < 0) {
            throw new IllegalArgumentException("Las edades no pueden ser negativas");
        }
        if (edadMinima > edadMaxima) {
            throw new IllegalArgumentException("La edad minima no puede ser mayor que la maxima");
        }
        this.edadMinima = edadMinima;
        this.edadMaxima = edadMaxima;
    }

    public boolean incluye(int edad) {
        return edad >= edadMinima && edad <= edadMaxima;
    }

    public int getEdadMinima() {
        return edadMinima;
    }

    public int getEdadMaxima() {
        return edadMaxima;
    }

    @Override
    public String toString() {
        return edadMinima + " - " + edadMaxima + " años";
    }
}
