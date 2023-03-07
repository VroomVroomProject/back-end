package com.backend.vroomvroom.entity.board;

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

    private String name;

    @Column(name = "admin_write_yn")
    private String adminWriteYn;

    private int orders;

    private String url;

    public PostCategoryEntity(Long id, String name, String adminWriteYn, int orders, String url, String useYn) {
        super(useYn);
        this.id = id;
        this.name = name;
        this.adminWriteYn = adminWriteYn;
        this.orders = orders;
        this.url = url;
    }
}
