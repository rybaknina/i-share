package by.ryni.share;

import by.ryni.share.dto.donate.DonateDto;
import by.ryni.share.dto.donate.DonateShortDto;
import by.ryni.share.entity.Donate;
import by.ryni.share.mapper.DonateMapper;
import by.ryni.share.repository.DonateRepository;
import by.ryni.share.service.DonateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("donateService")
public class DefaultDonateService extends AbstractService<DonateDto, DonateShortDto, Donate, DonateRepository> implements DonateService {
//    public DefaultDonateService() {
//    }
//
    @Autowired
    public DefaultDonateService(@Qualifier("donateRepository") DonateRepository repository, DonateMapper mapper) {
        super(repository, mapper);
    }
}
