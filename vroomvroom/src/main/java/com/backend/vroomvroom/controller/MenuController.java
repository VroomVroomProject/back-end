package com.backend.vroomvroom.controller;

import com.backend.vroomvroom.dto.menu.MenuDto;
import com.backend.vroomvroom.service.menu.IMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/vroom/menu")
public class MenuController {
    private final IMenuService menuService;

    @PostMapping("/create")
    public MenuDto createMenu(@RequestBody MenuDto menuDto) throws Exception {
        MenuDto createMenu = menuService.createMenu(menuDto);
        return createMenu;
    }

    @GetMapping("/detail/{menuId}")
    public MenuDto detailMenu(@PathVariable("menuId") String menuId) {
        MenuDto detailMenu = menuService.getDetailMenu(menuId);
        return detailMenu;
    }
}
