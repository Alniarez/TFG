package model;

import persistence.exception.PersistenceException;

import java.util.List;

/**
 * Todos los objetos del modelo que tienen una representación en la base de
 * datos implementan esta interface. Con ello todos tienen acceso CRUD a la base
 * de datos.
 * <p>
 * Los métodos adicionales específicos de cada clase del modelo se encuentran en
 * cada clase y son los mismos que hay en cada DAO.
 * <p>
 * Este ActiveRecord es una fachada a la capa de persistencia para perminitr un
 * uso mas fluido y legible a la hora de porgramar.
 *
 * @param <T>
 *            La clase del modelo en cuestión.
 * @author Jorge
 */
public interface ActiveRecord<T> {

    /**
     * En una sola transacción guarda en la base de datos el objeto.
     *
     * @return primary key del objeto insertado.
     * @throws PersistenceException
     */
    Object save() throws PersistenceException;

    /**
     * En una sola transacción actualiza los datos del objeto.
     *
     * @return nº de filas modificadas (presumiblemente 1 siempre).
     * @throws PersistenceException
     */
    Integer update() throws PersistenceException;

    /**
     * En una sola transacción elimina el objeto.
     *
     * @return nº de filas modificadas (presumiblemente 1 siempre).
     * @throws PersistenceException
     */
    Integer delete() throws PersistenceException;

    /**
     * Busca el objeto en la base de datos con los mismos valores de primay key.
     *
     * @return el objeto buscado o null (no encontrado).
     */
    T one();

    /**
     * Devuelve una lista de objetos que representan todas las entradas en la
     * base de datos.
     *
     * @return Una lista de objetos, puede estar vacía.
     */
    List<? extends T> all();

}
