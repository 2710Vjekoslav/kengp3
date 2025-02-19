package org.example.rest.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagination<T> {
    private List<T> content;
    private Integer page;
    private Integer size;
}