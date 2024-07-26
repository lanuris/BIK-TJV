package fit.lunevale.server.data.domain;

/**
 * Base class for domain entities enabling using IDs for identifying objects
 * @param <ID> ID type
 */
public interface EntityWithId<ID> {

    /**
     * ID getter
     * @return Entity ID
     */
    ID getId();
}
