package com.backend.vroomvroom.entity.menu;

import com.backend.vroomvroom.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tm_menu")
@Getter
public class MenuEntity extends BaseEntity {
    @Id
    @Column(name = "menu_id")
    private String menuId;
    @Column(name = "menu_nm")
    private String menuName;
    @Column(name = "menu_path")
    private String menuPath;
    @Column(name = "parent_id")
    private String parentId;
    @Column(name = "group_idx")
    private Long groupIdx;
    @Column(name = "level_no")
    private Long levelNo;
    @Column(name = "sort_no")
    private Long sortNo;

    @Builder
    public MenuEntity(String menuId, String menuName, String menuPath, String parentId, Long groupIdx, Long levelNo, Long sortNo) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuPath = menuPath;
        this.parentId = parentId;
        this.groupIdx = groupIdx;
        this.levelNo = levelNo;
        this.sortNo = sortNo;
    }
}
