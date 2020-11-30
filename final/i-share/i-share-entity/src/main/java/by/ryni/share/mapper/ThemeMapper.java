package by.ryni.share.mapper;

import by.ryni.share.dto.ThemeDto;
import by.ryni.share.entity.Theme;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel  = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface ThemeMapper extends GenericMapper<ThemeDto, Theme> {
    ThemeMapper instance = Mappers.getMapper(ThemeMapper.class);
}
