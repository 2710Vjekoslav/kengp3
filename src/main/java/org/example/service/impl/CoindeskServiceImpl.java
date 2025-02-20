package org.example.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.logstash.logback.util.StringUtils;
import org.example.persistence.repository.CurrencyRepository;
import org.example.service.CoindeskService;
import org.example.service.client.CoindeskClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;

@Service
public class CoindeskServiceImpl implements CoindeskService {

    @Autowired
    private CoindeskClient coindeskClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    public String getEnhancedCoindesk() throws JsonProcessingException {

        String coindesk = coindeskClient.getCoindesk();
        if (StringUtils.isBlank(coindesk)) {
            throw new RuntimeException("coindesk response is empty");
        }
        JsonNode rootNode = objectMapper.readTree(coindesk);
        JsonNode bpiNode = rootNode.path("bpi");

        Iterator<Map.Entry<String, JsonNode>> fields = bpiNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String code = entry.getKey();
            JsonNode currencyInfo = entry.getValue();

            currencyRepository.findByCodeAndLocale(code, "zh-TW").ifPresent((currency) -> {
                ((ObjectNode) currencyInfo).put("updateTime", currency.getUpdateTime().toString());
                ((ObjectNode) currencyInfo).put("chineseName", currency.getName());
            });
        }
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
    }
}
