package fit.lunevale.server.presentation.datatransfer.mapper;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public abstract class MapperImpl<T, DTO> implements Mapper<T, DTO> {

    protected final ModelMapper modelMapper;

    public MapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DTO toDTO(T post) {
        return modelMapper.map(post, getDtoClass());
    }

    public T toEntity(DTO postDTO) {
        return modelMapper.map(postDTO, getEntityClass());
    }

    public List<DTO> toDTOList(Iterable<T> posts) {
        List<DTO> dtoList = new ArrayList<>();
        for (T post : posts) {
            dtoList.add(toDTO(post));
        }
        return dtoList;
    }

    protected abstract Class<DTO> getDtoClass();

    protected abstract Class<T> getEntityClass();

}

