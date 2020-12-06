package org.senla.share.mapper;

import org.senla.share.dto.AbstractDto;
import org.senla.share.entity.AbstractEntity;
import org.mapstruct.MappingTarget;

public interface GenericMapper<D extends AbstractDto, E extends AbstractEntity> {
    D entityToDto(E entity);

    E dtoToEntity(D dto);

    E dtoToEntity(D dto, @MappingTarget E entity);
}
