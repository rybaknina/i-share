package by.ryni.share.mapper;

import by.ryni.share.dto.base.AbstractDto;
import by.ryni.share.dto.base.AbstractShortDto;
import by.ryni.share.entity.AbstractEntity;

public interface GenericMapper<D extends AbstractDto, H extends AbstractShortDto, E extends AbstractEntity> {
    D entityToDto(E entity);
    E dtoToEntity(D dto);
    E shortDtoToEntity(H shortDto);
    H entityToShortDto(E entity);
}
