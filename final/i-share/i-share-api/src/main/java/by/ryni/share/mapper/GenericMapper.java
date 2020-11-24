package by.ryni.share.mapper;

import by.ryni.share.dto.AbstractDto;
import by.ryni.share.entity.AbstractEntity;

public interface GenericMapper<D extends AbstractDto, E extends AbstractEntity> {
//    @Mapping(target = "authority", ignore = true)
    D entityToDto(E entity);
//    @Mapping(target = "authority", ignore = true)
    E dtoToEntity(D dto);
//    E shortDtoToEntity(H shortDto);
//    H entityToShortDto(E entity);
}
