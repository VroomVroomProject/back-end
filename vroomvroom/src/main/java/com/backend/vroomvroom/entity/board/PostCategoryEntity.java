package com.backend.vroomvroom.entity.board;

import com.backend.vroomvroom.dto.board.request.PostCategoryRequestDto;
import com.backend.vroomvroom.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "tm_post_category")
@Getter
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCategoryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_category_id", nullable = false)
    private Long id;

    @Column(name = "view_name")
    private String viewName;

    @Column(name = "url_name")
    private String urlName;

    @Column(name = "admin_write_yn")
    private String adminWriteYn;

    private int orders;

    private String url;

    public PostCategoryEntity(Long id, String viewName, String urlName, String adminWriteYn, int orders, String url, String useYn) {
        super(useYn);
        this.id = id;
        this.viewName = viewName;
        this.urlName = urlName;
        this.adminWriteYn = adminWriteYn;
        this.orders = orders;
        this.url = url;
    }

    public void update(PostCategoryRequestDto postCategoryRequestDto) {
        this.viewName = postCategoryRequestDto.getViewName();
        this.urlName = postCategoryRequestDto.getUrlName();
        this.adminWriteYn = postCategoryRequestDto.getAdminWriteYn();
        this.orders = postCategoryRequestDto.getOrders();
        this.url = postCategoryRequestDto.getUrl();
        // TODO: 2023-03-13 user 기능 구현 후에 update 시 updateUserId 세팅
    }

}
