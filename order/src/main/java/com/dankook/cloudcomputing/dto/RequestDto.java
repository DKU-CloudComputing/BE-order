package com.dankook.cloudcomputing.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDto {
    private String query;
    private String image;
    private String size;
    private Long userId;
}
