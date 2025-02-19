package org.example.rest.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class CurrencyRequest {

    private Long id;

    @NotBlank
    private String code;

    @NotBlank
    private String locale = "zh-TW";

    @NotBlank
    private String name;

    private String createBy;

    private String updateBy;
}
