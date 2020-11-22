package by.ryni.share.mapper;

import by.ryni.share.dto.theme.ThemeDto;
import by.ryni.share.dto.theme.ThemeShortDto;
import by.ryni.share.entity.Theme;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel  = "spring")
@Component
public interface ThemeMapper extends GenericMapper<ThemeDto, ThemeShortDto, Theme> {
    ThemeMapper instance = Mappers.getMapper(ThemeMapper.class);
}
