package org.example.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "coindesk-api", url = "${client.url.coindesk}")
public interface CoindeskClient {

    @GetMapping("/blog/coindesk.json")
    String getCoindesk();
}
