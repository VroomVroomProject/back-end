package com.backend.vroomvroom.service.menu;

import com.backend.vroomvroom.dto.menu.MenuDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements IMenuService{
    @Override
    public MenuDto createMenu(MenuDto menuDto) {
        return menuDto;
    }

    @Override
    public MenuDto getMenuDetail() {
        return null;
    }

    @Override
    public List<MenuDto> list() {
        return null;
    }

    @Override
    public List<MenuDto> listAll() {
        return null;
    }

    @Override
    public MenuDto updateMenu() {
        return null;
    }

    @Override
    public boolean deleteMenu() {
        return false;
    }
}
