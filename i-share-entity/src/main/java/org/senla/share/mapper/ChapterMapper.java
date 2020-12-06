package org.senla.share.mapper;

import org.senla.share.dto.ChapterDto;
import org.senla.share.entity.Chapter;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface ChapterMapper extends GenericMapper<ChapterDto, Chapter> {
    ChapterMapper instance = Mappers.getMapper(ChapterMapper.class);
}
