package com.backend.vroomvroom.dto.board;

import com.backend.vroomvroom.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class PostCategoryCreateDto extends BaseDto {
    @NotBlank
    private String name;
    @NotNull
    private int orders;
    @NotNull
    private String adminWriteYn;
    @NotNull
    private String url;
}
