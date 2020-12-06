package org.senla.share.service.impl;

import org.senla.share.dto.ThemeDto;
import org.senla.share.entity.Theme;
import org.senla.share.mapper.ThemeMapper;
import org.senla.share.repository.ThemeRepository;
import org.senla.share.service.ChapterService;
import org.senla.share.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service("themeService")
public class DefaultThemeService extends AbstractService<ThemeDto, Theme, ThemeRepository> implements ThemeService {
    private ChapterService chapterService;

    @Autowired
    public DefaultThemeService(@Qualifier("themeRepository") ThemeRepository themeRepository, ThemeMapper themeMapper) {
        super(themeRepository, themeMapper);
    }

    @Autowired
    public void setChapterService(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @Transactional
    @Override
    public Optional<ThemeDto> save(ThemeDto dto) {
        if (chapterService.getById(dto.getChapter().getId()).isPresent()) {
            return super.save(dto);
        } else {
            throw new EntityNotFoundException("Chapter for theme does not found");
        }
    }
}
