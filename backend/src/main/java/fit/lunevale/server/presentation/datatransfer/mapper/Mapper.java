package fit.lunevale.server.presentation.datatransfer.mapper;

import java.util.List;

/**
 * A base class for maping DTO objects to Domain objects
 *
 * @param <T> Domain type
 * @param <DTO> DTO type
 */
public interface Mapper<T, DTO> {
    /**
     * Converts Domain object to DTO
     *
     * @param post Domain object
     * @return DTO
     */
    DTO toDTO(T post);

    /**
     * Converts DTO object to Domain object
     *
     * @param postDTO DTO
     * @return Domain object
     */
    T toEntity(DTO postDTO);

    /**
     * Converts Domain object Iterable to list of DTOs
     *
     * @param posts Iterable of domain objects
     * @return List of DTOs
     */
    List<DTO> toDTOList(Iterable<T> posts);
}
