package com.backend.vroomvroom.dto.board.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Setter
@ToString
public class PostPageResponse {

    private List<PostResponseDto> postList;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;

    @Builder
    public PostPageResponse(List<PostResponseDto> postList, int pageNo, int pageSize, long totalElements, int totalPages, boolean last) {
        this.postList = postList;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
    }

    public static PostPageResponse mapToDto(Pageable pageable, Page<PostResponseDto> PostResponseDtoPage) {
        return PostPageResponse.builder()
                .postList(PostResponseDtoPage.getContent())
                .pageNo(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .totalElements(PostResponseDtoPage.getTotalElements())
                .totalPages(PostResponseDtoPage.getTotalPages())
                .last(PostResponseDtoPage.isLast())
                .build();
    }
}
