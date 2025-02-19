package org.example.service.convert;

import org.example.persistence.entity.Currency;
import org.example.service.bo.CurrencyBo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrencyConvert {

    Currency toEntity(CurrencyBo currencyBo);

    CurrencyBo toDomain(Currency currency);
}
