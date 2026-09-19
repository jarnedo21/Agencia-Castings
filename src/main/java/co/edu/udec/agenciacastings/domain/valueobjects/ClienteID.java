package co.edu.udec.agenciacastings.domain.valueobjects;

import java.util.Objects;
import java.util.UUID;

public class ClienteID {
    private final String value;

    public ClienteID() {
        this.value = UUID.randomUUID().toString();
    }

    public ClienteID(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El id no puede estar vacío");
        }
        this.value = value.trim();
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClienteID)) return false;
        return value.equals(((ClienteID) o).value);
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
