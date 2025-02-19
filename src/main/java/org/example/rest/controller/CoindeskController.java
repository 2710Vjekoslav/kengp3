package org.example.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.service.CoindeskService;
import org.example.service.client.CoindeskClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coindesk")
public class CoindeskController {

    @Autowired
    private CoindeskClient coindeskClient;

    @Autowired
    private CoindeskService coindeskService;

    @GetMapping("/basic")
    public String getCoindesk() {
        return coindeskClient.getCoindesk();
    }

    @GetMapping("/enhanced")
    public String getEnhancedCoindesk() throws JsonProcessingException {
        return coindeskService.getEnhancedCoindesk();
    }
}
