package org.senla.share.mapper;

import org.senla.share.dto.ThemeDto;
import org.senla.share.entity.Theme;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface ThemeMapper extends GenericMapper<ThemeDto, Theme> {
    ThemeMapper instance = Mappers.getMapper(ThemeMapper.class);
}
