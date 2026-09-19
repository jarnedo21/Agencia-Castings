package co.edu.udec.agenciacastings.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface Repositorio<T, ID> {

    void guardar(T entidad);

    Optional<T> buscarPorId(ID id);

    List<T> listar();

    List<T> buscar(Predicate<T> criterio);

    default boolean existe(ID id) {
        return buscarPorId(id).isPresent();
    }
}
