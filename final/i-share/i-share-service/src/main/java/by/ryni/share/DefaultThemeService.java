package by.ryni.share;

import by.ryni.share.api.ChapterRepository;
import by.ryni.share.dto.ThemeDto;
import by.ryni.share.entity.Theme;
import by.ryni.share.exception.ServiceException;
import by.ryni.share.mapper.ThemeMapper;
import by.ryni.share.api.ThemeRepository;
import by.ryni.share.api.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("themeService")
public class DefaultThemeService extends AbstractService<ThemeDto, Theme, ThemeRepository> implements ThemeService {
    private ChapterRepository chapterRepository;
    @Autowired
    public DefaultThemeService(@Qualifier("themeRepository") ThemeRepository repository, ThemeMapper mapper) {
        super(repository, mapper);
    }

    @Autowired
    public void setChapterRepository(ChapterRepository chapterRepository) {
        this.chapterRepository = chapterRepository;
    }

    @Transactional
    @Override
    public void save(ThemeDto dto) throws ServiceException {
        if (!chapterRepository.getById(dto.getChapterId()).isPresent()) {
            throw new ServiceException("Chapter does not exists");
        }
        super.save(dto);
    }
}
