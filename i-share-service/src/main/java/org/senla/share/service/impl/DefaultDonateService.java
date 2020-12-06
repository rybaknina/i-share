package org.senla.share.service.impl;

import org.senla.share.dto.CourseDto;
import org.senla.share.dto.DonateDto;
import org.senla.share.entity.Donate;
import org.senla.share.enums.DonateType;
import org.senla.share.mapper.DonateMapper;
import org.senla.share.repository.DonateRepository;
import org.senla.share.service.CourseService;
import org.senla.share.service.DonateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service("donateService")
public class DefaultDonateService extends AbstractService<DonateDto, Donate, DonateRepository> implements DonateService {
    private CourseService courseService;

    @Autowired
    public DefaultDonateService(@Qualifier("donateRepository") DonateRepository donateRepository, DonateMapper donateMapper) {
        super(donateRepository, donateMapper);
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @Transactional
    @Override
    public Optional<DonateDto> save(DonateDto dto) {
        Optional<CourseDto> course = courseService.getById(dto.getCourse().getId());
        if (course.isPresent()) {
            if (course.get().getDonateType() == DonateType.FREE ||
                    (dto.getDonation() != null && dto.getDonation().compareTo(BigDecimal.ZERO) < 1)) {
                throw new IllegalArgumentException("Donation is not needed");
            }
            if (course.get().getDonateType() == DonateType.FIXED) {
                dto.setDonation(course.get().getAmount());
                return super.save(dto);
            } else {
                return super.save(dto);
            }
        }
        return Optional.empty();
    }
}
