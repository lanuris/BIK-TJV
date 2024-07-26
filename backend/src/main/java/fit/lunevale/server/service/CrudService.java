package fit.lunevale.server.service;

/**
 * Common interface for connecting application classes to persistence layer.
 *
 * @param <T> type of stored object
 * @param <ID> type used for identification
 */
public interface CrudService<T, ID> {
    /**
     * Handles create part of CRUD
     *
     * @param e Object to be created
     * @return The created object
     */
    T create(T e);

    /**
     * Handles the read part of CRUD, reads the object specified by its ID
     *
     * @param id Objects ID
     * @return Optional of object, valid if the ID is found, empty otherwise
     */
    T readById(ID id) throws IllegalArgumentException;

    /**
     * Handles the read part of CRUD, reads all objects
     *
     * @return Iterable of all stored objects
     * @throws IllegalArgumentException if the entity with the given ID does not exist
     */
    Iterable<T> readAll();

    /**
     * Handles the update part of CRUD, edits the object specified by its ID
     *
     * @param id Objects ID
     * @param e new object data
     * @return the updated object
     * @throws IllegalArgumentException if the entity with the given ID does not exist
     */
    T update(ID id, T e) throws IllegalArgumentException;

    /**
     * Handles the update part of CRUD, edits the object specified by its ID passed in new objects data
     *
     * @param e new object data
     * @return the updated object
     * @throws IllegalArgumentException if the entity with the given ID does not exist
     */
    T update(T e) throws IllegalArgumentException;

    /**
     * Handles the delete part of CRUD, deletes the object specified by its ID
     * @param id Objects ID
     */
    void deleteById(ID id);
}

