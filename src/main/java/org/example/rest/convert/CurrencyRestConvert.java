package org.example.rest.convert;

import org.example.rest.request.CurrencyRequest;
import org.example.rest.vo.CurrencyVo;
import org.example.service.bo.CurrencyBo;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CurrencyRestConvert {
    CurrencyVo toVo(CurrencyBo currencyBo);

    CurrencyBo toDomain(CurrencyRequest currencyRequest);
}
