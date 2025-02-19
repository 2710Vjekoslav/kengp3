package org.example.service.bo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CurrencyBo {

    private Long id;

    private String code;

    private String locale;

    private String name;

    private String createBy;

    private String updateBy;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
