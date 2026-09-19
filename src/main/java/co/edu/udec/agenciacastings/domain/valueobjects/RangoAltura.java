package co.edu.udec.agenciacastings.domain.valueobjects;

public class RangoAltura {
    private final double alturaMinima;
    private final double alturaMaxima;

    public RangoAltura(double alturaMinima, double alturaMaxima) {
        if (alturaMinima < 0 || alturaMaxima < 0) {
            throw new IllegalArgumentException("Las alturas no pueden ser negativas");
        }
        if (alturaMinima > alturaMaxima) {
            throw new IllegalArgumentException("La altura minima no puede ser mayor que la maxima");
        }
        this.alturaMinima = alturaMinima;
        this.alturaMaxima = alturaMaxima;
    }

    public boolean incluye(double altura) {
        return altura >= alturaMinima && altura <= alturaMaxima;
    }

    public double getAlturaMinima() {
        return alturaMinima;
    }

    public double getAlturaMaxima() {
        return alturaMaxima;
    }

    @Override
    public String toString() {
        return alturaMinima + " - " + alturaMaxima + " m";
    }
}
