package by.ryni.share.mapper;

import by.ryni.share.dto.ChapterDto;
import by.ryni.share.entity.Chapter;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel  = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface ChapterMapper extends GenericMapper<ChapterDto, Chapter> {
    ChapterMapper instance = Mappers.getMapper(ChapterMapper.class);
}
