package by.ryni.share.mapper;

import by.ryni.share.dto.donate.DonateDto;
import by.ryni.share.dto.donate.DonateShortDto;
import by.ryni.share.entity.Donate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel  = "spring")
@Component
public interface DonateMapper extends GenericMapper<DonateDto, DonateShortDto, Donate> {
    DonateMapper instance = Mappers.getMapper(DonateMapper.class);
}
