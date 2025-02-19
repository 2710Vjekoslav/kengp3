package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface CoindeskService {

    String getEnhancedCoindesk() throws JsonProcessingException;
}
