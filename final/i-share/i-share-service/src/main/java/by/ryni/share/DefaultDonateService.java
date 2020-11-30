package by.ryni.share;

import by.ryni.share.api.CourseRepository;
import by.ryni.share.api.DonateRepository;
import by.ryni.share.api.DonateService;
import by.ryni.share.dto.DonateDto;
import by.ryni.share.entity.Course;
import by.ryni.share.entity.Donate;
import by.ryni.share.enums.DonateType;
import by.ryni.share.exception.ServiceException;
import by.ryni.share.mapper.DonateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service("donateService")
public class DefaultDonateService extends AbstractService<DonateDto, Donate, DonateRepository> implements DonateService {
    private CourseRepository courseRepository;
    @Autowired
    public DefaultDonateService(@Qualifier("donateRepository") DonateRepository repository, DonateMapper mapper) {
        super(repository, mapper);
    }

    @Autowired
    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Transactional
    @Override
    public void save(DonateDto dto) throws ServiceException {
        Optional<Course> course = courseRepository.getById(dto.getCourseId());
        if (!course.isPresent()) {
            throw new ServiceException("Course does not exist");
        }
        if (course.get().getDonateType() == DonateType.FREE) {
            throw new ServiceException("No need to donate");
        }
        if (course.get().getDonateType() == DonateType.FIXED) {
            dto.setDonation(course.get().getAmount());
        } else if (dto.getDonation().compareTo(BigDecimal.ZERO) < 1) {
            throw new ServiceException("Amount of donate must be entered");
        }
        super.save(dto);
    }
}
