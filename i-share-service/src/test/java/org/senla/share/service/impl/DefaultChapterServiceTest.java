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
import org.senla.share.entity.Chapter;
import org.senla.share.mapper.ChapterMapper;
import org.senla.share.repository.ChapterRepository;
import org.senla.share.service.ChapterService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultChapterServiceTest {
    private final static Logger logger = LogManager.getLogger(DefaultChapterServiceTest.class);
    private static List<Chapter> chapterList = new ArrayList<>();
    private static List<ChapterDto> chapterDtoList = new ArrayList<>();
    @Mock
    private ChapterRepository chapterRepository;
    @Mock
    private ChapterMapper chapterMapper;
    @InjectMocks
    private ChapterService chapterService = new DefaultChapterService(chapterRepository, chapterMapper);

    @BeforeEach
    void setUp() {
        Chapter chapter = new Chapter();
        chapter.setId(1);
        chapter.setName("Chapter 1");
        chapterList.add(chapter);
        ChapterDto chapterDto = new ChapterDto();
        chapterDto.setId(1);
        chapterDto.setName("Chapter 1");
        chapterDtoList.add(chapterDto);
    }

    @AfterEach
    void tearDown() {
        chapterList.clear();
        chapterDtoList.clear();
    }

    @Test
    void saveChapterShouldValidTest() {
        Chapter chapter = chapterList.get(0);
        ChapterDto chapterDto = chapterDtoList.get(0);
        when(chapterMapper.dtoToEntity(any(ChapterDto.class))).thenReturn(chapter);
        when(chapterMapper.entityToDto(any(Chapter.class))).thenReturn(chapterDto);
        when(chapterRepository.save(chapterMapper.dtoToEntity(chapterDto))).thenReturn(Optional.of(chapter));
        chapterService.save(chapterMapper.entityToDto(chapter));
    }

    @Test
    void saveChapterShouldThrowExceptionWhenAddNullTest() {
        assertThrows(NoSuchElementException.class, () -> chapterService.save(null));
    }

    @Test
    void deleteChapterShouldVerifyTest() {
        chapterService.delete(chapterList.get(0).getId());
        verify(chapterRepository, times(1)).delete(anyInt());
    }

    @Test
    void updateChapterShouldValidTest() {
        Chapter chapter = chapterList.get(0);
        ChapterDto chapterDto = chapterDtoList.get(0);
        when(chapterMapper.entityToDto(any(Chapter.class))).thenReturn(chapterDto);
        chapterService.update(chapterMapper.entityToDto(chapter));
    }

    @Test
    void getByIdShouldBeNotNullTest() {
        final int id = 0;
        given(chapterRepository.getById(id)).willReturn(Optional.of(chapterList.get(id)));
        assertNotNull(chapterService.getById(id));
    }

    @Test
    void getAllShouldReturnListTest() {
        when(chapterRepository.getAll()).thenReturn(chapterList);
        List<ChapterDto> expected = chapterService.getAll();
        assertEquals(chapterList.size(), expected.size());
    }

    @Test
    void getAllShouldThrowExceptionWhenEmptyTest() {
        when(chapterRepository.getAll()).thenReturn(null);
        assertThrows(NullPointerException.class, () -> chapterService.getAll());
    }
}