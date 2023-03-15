package com.backend.vroomvroom.controller;

import com.backend.vroomvroom.dto.menu.request.CreateMenuDto;
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
            throw new NullPointerException("빈 값");
        }
        return menuService.createMenu(createMenuDto);
    }

    /**
     * 메뉴 상세 정보
     * @param menuId
     * @return
     */
    @GetMapping("/detail/{menuId}")
    public MenuDto detailMenu(@PathVariable("menuId") String menuId) {
        if(StringUtils.isBlank(menuId)) {
            throw new NullPointerException("빈 값");
        }
        return menuService.getDetailMenu(menuId);
    }

    @GetMapping("/list")
    public List<MenuDto> menuList() {
        return menuService.list();
    }

    @GetMapping("/list/all")
    public List<MenuDto> menuAllList() {
        return menuService.listAll();
    }

    /**
     * 메뉴 삭제
     * @param menuDto
     * @return
     * @throws Exception
     */
    @PatchMapping("/delete")
    public Boolean deleteMenu(@RequestBody MenuDto menuDto) throws Exception {
//         || menuDto.getGroupIdx() == null
        if(StringUtils.isBlank(menuDto.getMenuId())) {
            throw new NullPointerException("빈 값");
        }
        return menuService.deleteMenu(menuDto);
    }
}
