package org.example.rest.controller;

import org.example.rest.convert.CurrencyRestConvert;
import org.example.rest.request.CurrencyRequest;
import org.example.rest.vo.CurrencyVo;
import org.example.rest.vo.Pagination;
import org.example.service.CurrencyService;
import org.example.service.bo.CurrencyBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/currency")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CurrencyRestConvert currencyRestConvert;

    @GetMapping()
    public ResponseEntity<Pagination<CurrencyVo>> getCurrencies(@RequestParam Integer page, @RequestParam(defaultValue = "100") Integer size) {
        List<CurrencyBo> currencyBos = currencyService.getCurrencies(page, size);
        List<CurrencyVo> currencyVos = currencyBos.stream()
                .map(currencyRestConvert::toVo)
                .collect(Collectors.toList());
        Pagination<CurrencyVo> pagination = new Pagination<CurrencyVo>(currencyVos, page, currencyVos.size());
        return ResponseEntity.ok(pagination);
    }

    @GetMapping("/{currencyCode}")
    public ResponseEntity<CurrencyVo> getCurrency(@PathVariable String currencyCode, @RequestParam(defaultValue = "zh-TW") String locale) {
        CurrencyVo currencyVo = currencyRestConvert.toVo(currencyService.getCurrency(currencyCode, locale));
        return ResponseEntity.ok(currencyVo);
    }

    @PostMapping()
    public ResponseEntity<Void> createCurrency(@Valid @RequestBody CurrencyRequest currencyRequest) {
        currencyRequest.setId(null);
        CurrencyBo currencyBo = currencyRestConvert.toDomain(currencyRequest);
        currencyService.createCurrency(currencyBo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping()
    public ResponseEntity<Void> updateCurrency(@Valid @RequestBody CurrencyRequest currencyRequest) throws MissingRequestValueException {

        if (Objects.isNull(currencyRequest.getId())) {
            throw new MissingRequestValueException("currency id should not be null");
        }
        CurrencyBo currencyBo = currencyRestConvert.toDomain(currencyRequest);
        currencyService.createCurrency(currencyBo);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurrency(@PathVariable Long id) {
        currencyService.deleteCurrency(id);
        return ResponseEntity.ok().build();
    }
}
