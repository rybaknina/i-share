package org.senla.share.mapper;

import org.senla.share.dto.DonateDto;
import org.senla.share.entity.Donate;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface DonateMapper extends GenericMapper<DonateDto, Donate> {
    DonateMapper instance = Mappers.getMapper(DonateMapper.class);
}
