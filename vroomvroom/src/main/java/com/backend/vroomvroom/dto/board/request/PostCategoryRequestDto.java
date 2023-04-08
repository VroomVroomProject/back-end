package com.backend.vroomvroom.dto.board.request;

import com.backend.vroomvroom.entity.board.PostCategoryEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class PostCategoryRequestDto {

    @NotBlank
    private String viewName;

    @NotBlank
    private String urlName;

    @NotNull
    private int orders;

    @NotNull
    private String adminWriteYn;

    @NotNull
    private String url;

    @NotNull
    private String useYn;

    public static PostCategoryEntity mapToEntity(PostCategoryRequestDto postCategoryRequestDto) {
        return PostCategoryEntity.builder()
                .viewName(postCategoryRequestDto.getViewName())
                .urlName(postCategoryRequestDto.getUrlName())
                .orders(postCategoryRequestDto.getOrders())
                .adminWriteYn(postCategoryRequestDto.getAdminWriteYn())
                .url(postCategoryRequestDto.getUrl())
                .useYn(postCategoryRequestDto.getUseYn())
                .build();
    }
}
