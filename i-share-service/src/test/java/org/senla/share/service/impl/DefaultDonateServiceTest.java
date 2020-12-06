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
import org.senla.share.dto.CourseDto;
import org.senla.share.dto.DonateDto;
import org.senla.share.entity.Course;
import org.senla.share.entity.Donate;
import org.senla.share.mapper.DonateMapper;
import org.senla.share.repository.DonateRepository;
import org.senla.share.service.CourseService;
import org.senla.share.service.DonateService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultDonateServiceTest {
    private final static Logger logger = LogManager.getLogger(DefaultDonateServiceTest.class);
    private static List<Donate> donateList = new ArrayList<>();
    private static List<DonateDto> donateDtoList = new ArrayList<>();
    @Mock
    private DonateRepository donateRepository;
    @Mock
    private DonateMapper donateMapper;
    @Mock
    private CourseService courseService;
    @InjectMocks
    private DonateService donateService = new DefaultDonateService(donateRepository, donateMapper);

    @BeforeEach
    void setUp() {
        Donate donate = new Donate();
        donate.setId(1);
        donate.setDonation(new BigDecimal(10.0));
        Course course = new Course();
        course.setId(1);
        donate.setCourse(course);
        donateList.add(donate);
        DonateDto donateDto = new DonateDto();
        donateDto.setId(1);
        donateDto.setDonation(new BigDecimal(10.0));
        CourseDto courseDto = new CourseDto();
        courseDto.setId(1);
        donateDto.setCourse(courseDto);
        donateDtoList.add(donateDto);
    }

    @AfterEach
    void tearDown() {
        donateList.clear();
        donateDtoList.clear();
    }

    @Test
    void saveDonateShouldValidTest() {
        DonateDto donateDto = donateDtoList.get(0);
        when(courseService.getById(anyInt())).thenReturn(Optional.of(donateDto.getCourse()));
        when(donateMapper.dtoToEntity(any(DonateDto.class))).thenReturn(donateList.get(0));
        when(donateMapper.entityToDto(any(Donate.class))).thenReturn(donateDto);
        when(donateRepository.save(donateMapper.dtoToEntity(donateDto))).thenReturn(Optional.of(donateList.get(0)));
        donateService.save(donateDto);
    }

    @Test
    void saveDonateShouldThrowExceptionWhenAddNullTest() {
        assertThrows(NullPointerException.class, () -> donateService.save(null));
    }

    @Test
    void deleteDonateShouldVerifyTest() {
        donateService.delete(donateList.get(0).getId());
        verify(donateRepository, times(1)).delete(anyInt());
    }

    @Test
    void updateDonateShouldValidTest() {
        Donate donate = donateList.get(0);
        DonateDto donateDto = donateDtoList.get(0);
        when(donateMapper.entityToDto(any(Donate.class))).thenReturn(donateDto);
        donateService.update(donateMapper.entityToDto(donate));
    }

    @Test
    void getByIdShouldBeNotNullTest() {
        final int id = 0;
        given(donateRepository.getById(id)).willReturn(Optional.of(donateList.get(id)));
        assertNotNull(donateService.getById(id));
    }

    @Test
    void getAllShouldReturnListTest() {
        when(donateRepository.getAll()).thenReturn(donateList);
        List<DonateDto> expected = donateService.getAll();
        assertEquals(donateList.size(), expected.size());
    }

    @Test
    void getAllShouldThrowExceptionWhenEmptyTest() {
        when(donateRepository.getAll()).thenReturn(null);
        assertThrows(NullPointerException.class, () -> donateService.getAll());
    }
}