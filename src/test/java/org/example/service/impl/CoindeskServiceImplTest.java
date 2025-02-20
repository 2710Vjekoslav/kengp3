package org.example.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.persistence.entity.Currency;
import org.example.persistence.repository.CurrencyRepository;
import org.example.service.client.CoindeskClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CoindeskServiceImplTest {

    @InjectMocks
    private CoindeskServiceImpl coindeskServiceImpl;

    @Mock
    private CoindeskClient coindeskClient;

    @Mock
    private CurrencyRepository currencyRepository;

    @Spy
    private ObjectMapper objectMapper;

    @ParameterizedTest
    @NullAndEmptySource // Includes both null and ""
    @ValueSource(strings = {" "})
    public void givenEmptyCoindesk_whenGetEnhancedCoindesk_thenRuntimeExceptionThrown(String coindesk) {

        when(coindeskClient.getCoindesk()).thenReturn(coindesk);

        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> coindeskServiceImpl.getEnhancedCoindesk());
        assertEquals("coindesk response is empty", runtimeException.getMessage());
    }

    @Test
    public void givenInvalidCoindesk_whenGetEnhancedCoindesk_thenNothingWasEnhanced() throws JsonProcessingException {

        String coindesk = "{\"test\":123}\n{\n  \"test\" : 123\n}";
        when(coindeskClient.getCoindesk()).thenReturn(coindesk);

        String actualEnhancedCoindesk = coindeskServiceImpl.getEnhancedCoindesk();

        ObjectMapper forAssertionObjectMapper = new ObjectMapper();
        JsonNode expectedJsonNode = forAssertionObjectMapper.readTree(coindesk);
        JsonNode actualJsonNode = forAssertionObjectMapper.readTree(actualEnhancedCoindesk);

        assertEquals(expectedJsonNode, actualJsonNode);
    }

    @Test
    public void givenCoindesk_whenGetEnhancedCoindesk_thenEnhancedCoindeskReturned() throws JsonProcessingException {

        String coindesk = "{\n" +
                "  \"time\": {\n" +
                "    \"updated\": \"Sep 2, 2024 07:07:20 UTC\",\n" +
                "    \"updatedISO\": \"2024-09-02T07:07:20+00:00\",\n" +
                "    \"updateduk\": \"Sep 2, 2024 at 08:07 BST\"\n" +
                "  },\n" +
                "  \"disclaimer\": \"just for test\",\n" +
                "  \"chartName\": \"Bitcoin\",\n" +
                "  \"bpi\": {\n" +
                "    \"USD\": {\n" +
                "      \"code\": \"USD\",\n" +
                "      \"symbol\": \"&#36;\",\n" +
                "      \"rate\": \"57,756.298\",\n" +
                "      \"description\": \"United States Dollar\",\n" +
                "      \"rate_float\": 57756.2984\n" +
                "    },\n" +
                "    \"GBP\": {\n" +
                "      \"code\": \"GBP\",\n" +
                "      \"symbol\": \"&pound;\",\n" +
                "      \"rate\": \"43,984.02\",\n" +
                "      \"description\": \"British Pound Sterling\",\n" +
                "      \"rate_float\": 43984.0203\n" +
                "    },\n" +
                "    \"EUR\": {\n" +
                "      \"code\": \"EUR\",\n" +
                "      \"symbol\": \"&euro;\",\n" +
                "      \"rate\": \"52,243.287\",\n" +
                "      \"description\": \"Euro\",\n" +
                "      \"rate_float\": 52243.2865\n" +
                "    }\n" +
                "  }\n" +
                "}";

        String expectedEnhancedCoindesk = "{\n" +
                "    \"time\": {\n" +
                "      \"updated\": \"Sep 2, 2024 07:07:20 UTC\",\n" +
                "      \"updatedISO\": \"2024-09-02T07:07:20+00:00\",\n" +
                "      \"updateduk\": \"Sep 2, 2024 at 08:07 BST\"\n" +
                "    },\n" +
                "    \"disclaimer\": \"just for test\",\n" +
                "    \"chartName\": \"Bitcoin\",\n" +
                "    \"bpi\": {\n" +
                "      \"USD\": {\n" +
                "        \"code\": \"USD\",\n" +
                "        \"symbol\": \"&#36;\",\n" +
                "        \"rate\": \"57,756.298\",\n" +
                "        \"description\": \"United States Dollar\",\n" +
                "        \"rate_float\": 57756.2984,\n" +
                "        \"chineseName\": \"美元\",\n" +
                "        \"updateTime\":  \"2024-09-02T14:30\"\n" +
                "      },\n" +
                "      \"GBP\": {\n" +
                "        \"code\": \"GBP\",\n" +
                "        \"symbol\": \"&pound;\",\n" +
                "        \"rate\": \"43,984.02\",\n" +
                "        \"description\": \"British Pound Sterling\",\n" +
                "        \"rate_float\": 43984.0203\n" +
                "      },\n" +
                "      \"EUR\": {\n" +
                "        \"code\": \"EUR\",\n" +
                "        \"symbol\": \"&euro;\",\n" +
                "        \"rate\": \"52,243.287\",\n" +
                "        \"description\": \"Euro\",\n" +
                "        \"rate_float\": 52243.2865\n" +
                "      }\n" +
                "    }\n" +
                "  }";

        when(coindeskClient.getCoindesk()).thenReturn(coindesk);

        Currency usd = new Currency();
        usd.setName("美元");
        String dateTimeString = "2024-09-02 14:30:00"; // Example date string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, formatter);
        usd.setUpdateTime(localDateTime);
        when(currencyRepository.findByCodeAndLocale(eq("USD"), eq("zh-TW"))).thenReturn(Optional.of(usd), Optional.empty(), Optional.empty());

        String actualEnhancedCoindesk = coindeskServiceImpl.getEnhancedCoindesk();
        ObjectMapper forAssertionObjectMapper = new ObjectMapper();
        JsonNode expectedJsonNode = forAssertionObjectMapper.readTree(expectedEnhancedCoindesk);
        JsonNode actualJsonNode = forAssertionObjectMapper.readTree(actualEnhancedCoindesk);

        assertEquals(expectedJsonNode, actualJsonNode);
    }
}
