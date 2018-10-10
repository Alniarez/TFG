package persistence;

import persistence.exception.PersistenceException;

import java.util.List;

/**
 * Todos los objetos del modelo que tienen una representación en la base de
 * datos tienen un DAO. Con ello todos tienen acceso CRUD a la base de datos.
 * <p>
 * A pesar de ello no se espera que todos estos métodos se puedan utilizar en
 * cualquier momento. La llamada a estos métodos no producirá una rotura de la
 * integridad estructural de la base de datos (Integrity Constraints Violation).
 * <p>
 * Los métodos adicionales específicos de cada clase del modelo se encuentran en
 * cada clase.
 *
 * @param <T>
 *            La clase del modelo en cuestión.
 * @author Jorge
 */
public interface DAO<T> {

    /**
     * En una sola transacción guarda en la base de datos el objeto.
     *
     * @return primary key del objeto insertado.
     * @throws PersistenceException
     */
    Object save(T pojo) throws PersistenceException;

    /**
     * En una sola transacción actualiza los datos del objeto.
     *
     * @return nº de filas modificadas (presumiblemente 1 siempre).
     * @throws PersistenceException
     */
    Integer update(T pojo) throws PersistenceException;

    /**
     * En una sola transacción elimina el objeto.
     *
     * @return nº de filas modificadas (presumiblemente 1 siempre).
     */
    Integer delete(T pojo) throws PersistenceException;

    /**
     * Busca el objeto en la base de datos con los mismos valores de primay key.
     *
     * @return el objeto buscado o null (no encontrado).
     */
    T one(T pojo);

    /**
     * Devuelve una lista de objetos que representan todas las entradas en la
     * base de datos.
     *
     * @return Una lista de objetos, puede estar vacía.
     */
    List<? extends T> all(T pojo);

}
