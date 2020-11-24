package by.ryni.share;

import by.ryni.share.dto.ChapterDto;
import by.ryni.share.entity.Chapter;
import by.ryni.share.mapper.ChapterMapper;
import by.ryni.share.repository.ChapterRepository;
import by.ryni.share.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("chapterService")
public class DefaultChapterService extends AbstractService<ChapterDto, Chapter, ChapterRepository> implements ChapterService {
    @Autowired
    public DefaultChapterService(@Qualifier("chapterRepository") ChapterRepository repository, ChapterMapper mapper) {
        super(repository, mapper);
    }
}
