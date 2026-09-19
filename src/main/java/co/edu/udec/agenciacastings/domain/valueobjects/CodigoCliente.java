package co.edu.udec.agenciacastings.domain.valueobjects;

import java.util.Objects;

public class CodigoCliente {
    private final String value;

    public CodigoCliente(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El código de cliente no puede estar vacío");
        }
        this.value = value.trim();
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CodigoCliente)) return false;
        return value.equals(((CodigoCliente) o).value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
