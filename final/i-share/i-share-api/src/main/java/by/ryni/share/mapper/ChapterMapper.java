package by.ryni.share.mapper;

import by.ryni.share.dto.chapter.ChapterDto;
import by.ryni.share.dto.chapter.ChapterShortDto;
import by.ryni.share.entity.Chapter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel  = "spring")
@Component
public interface ChapterMapper extends GenericMapper<ChapterDto, ChapterShortDto, Chapter> {
    ChapterMapper instance = Mappers.getMapper(ChapterMapper.class);
}
