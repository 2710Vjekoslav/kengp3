package org.example.service;

import org.example.service.bo.CurrencyBo;

import java.util.List;

public interface CurrencyService {

    List<CurrencyBo> getCurrencies(Integer pageSize, Integer index);

    CurrencyBo getCurrency(String currencyCode, String locale);

    Long createCurrency(CurrencyBo currencyBo);

    void updateCurrency(CurrencyBo currencyBo);

    void deleteCurrency(Long id);
}
