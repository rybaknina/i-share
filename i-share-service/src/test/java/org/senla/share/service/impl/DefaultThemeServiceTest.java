package org.senla.share.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.senla.share.dto.ChapterDto;
import org.senla.share.dto.ThemeDto;
import org.senla.share.entity.Chapter;
import org.senla.share.entity.Theme;
import org.senla.share.mapper.ThemeMapper;
import org.senla.share.repository.ThemeRepository;
import org.senla.share.service.ChapterService;
import org.senla.share.service.ThemeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultThemeServiceTest {
    private final static Logger logger = LogManager.getLogger(DefaultThemeServiceTest.class);
    private static List<Theme> themeList = new ArrayList<>();
    private static List<ThemeDto> themeDtoList = new ArrayList<>();
    @Mock
    private ThemeRepository themeRepository;
    @Mock
    private ThemeMapper themeMapper;
    @Mock
    private ChapterService chapterService;
    @InjectMocks
    private ThemeService themeService = new DefaultThemeService(themeRepository, themeMapper);

    @BeforeEach
    void setUp() {
        Theme theme = new Theme();
        theme.setId(1);
        Chapter chapter = new Chapter();
        chapter.setId(1);
        theme.setChapter(chapter);
        themeList.add(theme);
        ThemeDto themeDto = new ThemeDto();
        themeDto.setId(1);
        ChapterDto chapterDto = new ChapterDto();
        chapterDto.setId(1);
        themeDto.setChapter(chapterDto);
        themeDtoList.add(themeDto);
    }


    @AfterEach
    void tearDown() {
        themeList.clear();
        themeDtoList.clear();
    }

    @Test
    void saveThemeShouldValidTest() {
        ThemeDto themeDto = themeDtoList.get(0);
        when(chapterService.getById(anyInt())).thenReturn(Optional.of(themeDto.getChapter()));
        when(themeMapper.dtoToEntity(any(ThemeDto.class))).thenReturn(themeList.get(0));
        when(themeMapper.entityToDto(any(Theme.class))).thenReturn(themeDto);
        when(themeRepository.save(themeMapper.dtoToEntity(themeDto))).thenReturn(Optional.of(themeList.get(0)));
        themeService.save(themeDto);
    }

    @Test
    void saveThemeShouldThrowExceptionWhenAddNullTest() {
        assertThrows(NullPointerException.class, () -> themeService.save(null));
    }

    @Test
    void deleteThemeShouldVerifyTest() {
        themeService.delete(themeList.get(0).getId());
        verify(themeRepository, times(1)).delete(anyInt());
    }

    @Test
    void updateThemeShouldValidTest() {
        Theme theme = themeList.get(0);
        ThemeDto themeDto = themeDtoList.get(0);
        when(themeMapper.entityToDto(any(Theme.class))).thenReturn(themeDto);
        themeService.update(themeMapper.entityToDto(theme));
    }

    @Test
    void getByIdShouldBeNotNullTest() {
        final int id = 0;
        given(themeRepository.getById(id)).willReturn(Optional.of(themeList.get(id)));
        assertNotNull(themeService.getById(id));
    }

    @Test
    void getAllShouldReturnListTest() {
        when(themeRepository.getAll()).thenReturn(themeList);
        List<ThemeDto> expected = themeService.getAll();
        assertEquals(themeList.size(), expected.size());
    }

    @Test
    void getAllShouldThrowExceptionWhenEmptyTest() {
        when(themeRepository.getAll()).thenReturn(null);
        assertThrows(NullPointerException.class, () -> themeService.getAll());
    }
}