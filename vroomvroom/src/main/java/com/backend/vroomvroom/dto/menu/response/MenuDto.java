package com.backend.vroomvroom.dto.menu.response;

import com.backend.vroomvroom.dto.BaseDto;
import com.backend.vroomvroom.entity.menu.MenuEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class MenuDto extends BaseDto {
    private String menuId;
    private String menuName;
    private String menuPath;
    private String parentId;
    private Long groupIdx;
    private Long levelNo;
    private Long sortNo;

    @Builder
    public MenuDto(MenuEntity menuEntity) {
        this.menuId = menuEntity.getMenuId();
        this.menuName = menuEntity.getMenuName();
        this.menuPath = menuEntity.getMenuPath();
        this.parentId = menuEntity.getParentId();
        this.groupIdx = menuEntity.getGroupIdx();
        this.levelNo = menuEntity.getLevelNo();
        this.sortNo = menuEntity.getSortNo();
    }

    public static MenuDto mapToDto(MenuEntity menuEntity) {
        return MenuDto.builder()
                .menuEntity(menuEntity)
                .build();
    }
}
