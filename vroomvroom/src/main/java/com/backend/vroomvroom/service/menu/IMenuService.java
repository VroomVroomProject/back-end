package com.backend.vroomvroom.service.menu;

import com.backend.vroomvroom.dto.menu.MenuDto;
import com.backend.vroomvroom.entity.menu.MenuEntity;

import java.util.List;

public interface IMenuService {
    public MenuDto createMenu(MenuDto menuDto);

    /**
     * menu 상세정보
     * @return
     */
    public MenuDto getMenuDetail();

    /**
     * menu use_yn y 사용
     * @return
     */
    public List<MenuDto> list();

    /**
     * menu use_yn y,n 상관없이 사용
     * @return
     */
    public List<MenuDto> listAll();

    public MenuDto updateMenu();

    public boolean deleteMenu();

}
