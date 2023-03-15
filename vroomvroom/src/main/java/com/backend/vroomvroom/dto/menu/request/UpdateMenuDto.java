package com.backend.vroomvroom.dto.menu.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class UpdateMenuDto {
    @NotBlank(message = "메뉴아이디를 입력해주세요")
    @Size(min = 1, max = 8, message = "메뉴아이디는 1~8자 입니다")
    private String menuId;
    @NotNull
    private String menuName;
    @NotNull
    private String menuPath;
    private String parentId;
    private Long groupIdx;
    private Long levelNo;
    private Long sortNo;
    private Long changeSortNo;
}
