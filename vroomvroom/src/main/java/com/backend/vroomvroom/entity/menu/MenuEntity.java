package com.backend.vroomvroom.entity.menu;

import com.backend.vroomvroom.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tm_menu")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public MenuEntity(String menuId, String menuName, String menuPath, String parentId, Long groupIdx, Long levelNo, Long sortNo, String useYn) {
        super(useYn);
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuPath = menuPath;
        this.parentId = parentId;
        this.groupIdx = groupIdx;
        this.levelNo = levelNo;
        this.sortNo = sortNo;
    }
}
