package by.ryni.share.mapper;

import by.ryni.share.dto.AbstractDto;
import by.ryni.share.entity.AbstractEntity;
import org.mapstruct.MappingTarget;

public interface GenericMapper<D extends AbstractDto, E extends AbstractEntity> {
    D entityToDto(E entity);
    E dtoToEntity(D dto);
    E dtoToEntity(D dto, @MappingTarget E entity);
}
