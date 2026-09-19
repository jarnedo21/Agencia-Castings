package co.edu.udec.agenciacastings.domain.valueobjects;

public class Costo {
    private final double monto;

    public Costo(double monto) {
        if (monto < 0) {
            throw new IllegalArgumentException("El costo no puede ser negativo");
        }
        this.monto = monto;
    }

    public double getMonto() {
        return monto;
    }

    @Override
    public String toString() {
        return String.format("$%,.2f", monto);
    }
}
