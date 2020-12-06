package org.senla.share.service.impl;

import org.senla.share.dto.ChapterDto;
import org.senla.share.entity.Chapter;
import org.senla.share.mapper.ChapterMapper;
import org.senla.share.repository.ChapterRepository;
import org.senla.share.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("chapterService")
public class DefaultChapterService extends AbstractService<ChapterDto, Chapter, ChapterRepository> implements ChapterService {
    @Autowired
    public DefaultChapterService(@Qualifier("chapterRepository") ChapterRepository chapterRepository, ChapterMapper chapterMapper) {
        super(chapterRepository, chapterMapper);
    }
}
