package com.backend.vroomvroom.controller;

import com.backend.vroomvroom.dto.menu.MenuDto;
import com.backend.vroomvroom.service.menu.IMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/vroom/menu")
public class MenuController {
    private final IMenuService menuService;

    @PostMapping("/create")
    public MenuDto createMenu(@RequestBody MenuDto menuDto) {
        MenuDto createMenu = menuService.createMenu(menuDto);
        return createMenu;
    }
}
