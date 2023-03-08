package com.backend.vroomvroom.dto.board.response;

import com.backend.vroomvroom.entity.board.PostCategoryEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostCategoryResponseDto {

    private Long postCategoryId;

    private String name;

    private int orders;

    private String adminWriteYn;

    @Builder
    public PostCategoryResponseDto(Long postCategoryId, String name, int orders, String adminWriteYn) {
        this.postCategoryId = postCategoryId;
        this.name = name;
        this.orders = orders;
        this.adminWriteYn = adminWriteYn;
    }

    public static PostCategoryResponseDto mapToDto (PostCategoryEntity postCategoryEntity) {
        return PostCategoryResponseDto.builder()
                .postCategoryId(postCategoryEntity.getId())
                .name(postCategoryEntity.getName())
                .orders(postCategoryEntity.getOrders())
                .adminWriteYn(postCategoryEntity.getAdminWriteYn())
                .build();
    }
}
