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

    @NotNull
    private Long userId;

    @NotBlank
    private String name;

    @NotNull
    private int orders;

    @NotNull
    private String adminWriteYn;

    @NotNull
    private String url;

    public static PostCategoryEntity mapToEntity(PostCategoryRequestDto postCategoryRequestDto) {
        return PostCategoryEntity.builder()
                .name(postCategoryRequestDto.getName())
                .orders(postCategoryRequestDto.getOrders())
                .adminWriteYn(postCategoryRequestDto.getAdminWriteYn())
                .url(postCategoryRequestDto.getUrl())
                .useYn("Y")
                .createUserId(postCategoryRequestDto.getUserId()).build();
    }
}
