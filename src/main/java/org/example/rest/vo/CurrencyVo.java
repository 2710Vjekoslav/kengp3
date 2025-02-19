package org.example.rest.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CurrencyVo {

    private Long id;

    private String code;

    private String locale;

    private String name;

    private String createBy;

    private String updateBy;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
