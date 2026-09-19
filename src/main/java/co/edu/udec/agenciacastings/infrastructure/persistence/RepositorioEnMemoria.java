package co.edu.udec.agenciacastings.infrastructure.persistence;

import co.edu.udec.agenciacastings.domain.repository.Repositorio;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Repositorio que guarda las entidades en memoria (se pierden al cerrar el programa).
 */
public class RepositorioEnMemoria<T, ID> implements Repositorio<T, ID> {
    private final Map<ID, T> datos = new LinkedHashMap<>();
    private final Function<T, ID> obtenerId;

    public RepositorioEnMemoria(Function<T, ID> obtenerId) {
        this.obtenerId = obtenerId;
    }

    @Override
    public void guardar(T entidad) {
        datos.put(obtenerId.apply(entidad), entidad);
    }

    @Override
    public Optional<T> buscarPorId(ID id) {
        return Optional.ofNullable(datos.get(id));
    }

    @Override
    public List<T> listar() {
        return new ArrayList<>(datos.values());
    }

    @Override
    public List<T> buscar(Predicate<T> criterio) {
        return datos.values().stream().filter(criterio).toList();
    }
}
