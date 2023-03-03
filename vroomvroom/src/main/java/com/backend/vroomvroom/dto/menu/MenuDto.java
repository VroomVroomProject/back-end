package com.backend.vroomvroom.dto.menu;

import com.backend.vroomvroom.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MenuDto extends BaseDto {
    private String menuId;
    private String menuName;
    private String menuPath;
    private String parentId;
    private Long groupIdx;
    private Long levelNo;
    private Long sortNo;
}
