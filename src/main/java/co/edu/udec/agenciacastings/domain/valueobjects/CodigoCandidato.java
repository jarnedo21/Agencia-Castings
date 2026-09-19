package co.edu.udec.agenciacastings.domain.valueobjects;

import java.util.Objects;

public class CodigoCandidato {
    private final String value;

    public CodigoCandidato(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El código de candidato no puede estar vacío");
        }
        this.value = value.trim();
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CodigoCandidato)) return false;
        return value.equals(((CodigoCandidato) o).value);
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
