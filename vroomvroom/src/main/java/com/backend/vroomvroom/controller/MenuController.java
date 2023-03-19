package com.backend.vroomvroom.controller;

import com.backend.vroomvroom.dto.menu.request.CreateMenuDto;
import com.backend.vroomvroom.dto.menu.request.UpdateMenuDto;
import com.backend.vroomvroom.dto.menu.response.MenuDto;
import com.backend.vroomvroom.service.menu.IMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/vroom/menu")
public class MenuController {
    private final IMenuService menuService;

    /**
     * 메뉴 생성
     * @param createMenuDto
     * @return
     * @throws Exception
     */
    @PostMapping("/create")
    public MenuDto createMenu(@RequestBody @Valid CreateMenuDto createMenuDto) throws Exception {
        if(StringUtils.isBlank(createMenuDto.getMenuId())) {
            throw new NullPointerException("메뉴 ID가 빈 값입니다");
        }
        return menuService.createMenu(createMenuDto);
    }

    /**
     * 메뉴 수정
     * @param updateMenuDto
     * @return
     * @throws Exception
     */
    @PatchMapping("/update")
    public MenuDto updateMenu(@RequestBody @Valid UpdateMenuDto updateMenuDto) throws Exception {
        if(StringUtils.isBlank(updateMenuDto.getMenuId())) {
            throw new NullPointerException("메뉴 ID가 빈 값입니다");
        }
        return menuService.updateMenu(updateMenuDto);
    }

    /**
     * 메뉴 상세 정보
     * @param menuId
     * @return
     */
    @GetMapping("/detail/{menuId}")
    public MenuDto detailMenu(@PathVariable("menuId") String menuId) {
        if(StringUtils.isBlank(menuId)) {
            throw new NullPointerException("메뉴 ID가 빈 값입니다");
        }
        return menuService.getDetailMenu(menuId);
    }

    /**
     * 메뉴 목록
     * @return
     */
    @GetMapping("/list")
    public List<MenuDto> menuList() {
        return menuService.list();
    }

    /**
     * 메뉴 목록 (사용 여부 관계 없이)
     * @return
     */
    @GetMapping("/list/all")
    public List<MenuDto> menuAllList() {
        return menuService.listAll();
    }

    /**
     * 메뉴 삭제
     * @param menuId
     * @return
     * @throws Exception
     */
    @PatchMapping("/delete/{menuId}")
    public Boolean deleteMenu(@PathVariable("menuId") String menuId) throws Exception {
        if(StringUtils.isBlank(menuId)) {
            throw new NullPointerException("메뉴 ID가 빈 값입니다");
        }
        return menuService.deleteMenu(menuId);
    }
}
