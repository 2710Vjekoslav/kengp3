package org.example.service.impl;

import org.example.persistence.entity.Currency;
import org.example.persistence.repository.CurrencyRepository;
import org.example.service.CurrencyService;
import org.example.service.bo.CurrencyBo;
import org.example.service.convert.CurrencyConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CurrencyConvert currencyConvert;

    @Override
    public List<CurrencyBo> getCurrencies(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Currency> currencies = currencyRepository.findAll(pageRequest);
        List<CurrencyBo> currencyBos = currencies.stream()
                .map(currencyConvert::toDomain)
                .collect(Collectors.toList());
        return currencyBos;
    }

    @Override
    public CurrencyBo getCurrency(String currencyCode, String locale) {
        return currencyRepository.findByCodeAndLocale(currencyCode, locale)
                .map(currencyConvert::toDomain)
                .orElse(null);
    }

    @Override
    public void updateCurrency(CurrencyBo currencyBo) {
        Currency currency = currencyConvert.toEntity(currencyBo);
        currencyRepository.save(currency);
    }

    @Override
    public Long createCurrency(CurrencyBo currencyBo) {
        Currency currency = currencyConvert.toEntity(currencyBo);
        currencyRepository.save(currency);
        return currency.getId();
    }

    @Override
    public void deleteCurrency(Long id) {

        int deleted = currencyRepository.deleteCurrencyById(id);
        if (deleted != 1) {
            throw new NoSuchElementException("currency to delete not found");
        }
    }
}
