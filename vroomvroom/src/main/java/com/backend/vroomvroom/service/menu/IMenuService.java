package com.backend.vroomvroom.service.menu;

import com.backend.vroomvroom.dto.menu.request.CreateMenuDto;
import com.backend.vroomvroom.dto.menu.request.UpdateMenuDto;
import com.backend.vroomvroom.dto.menu.response.MenuDto;

import java.util.List;

public interface IMenuService {
    /**
     * 메뉴 생성
     * @param createMenuDto
     * @return
     * @throws Exception
     */
    public MenuDto createMenu(CreateMenuDto createMenuDto) throws Exception;

    /**
     * menu 상세정보
     * @return
     */
    public MenuDto getDetailMenu(String menuId);

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

    /**
     * menu 수정
     * @return
     */
    public MenuDto updateMenu(UpdateMenuDto updateMenuDto);

    /**
     * menu 삭제
     * @return
     */
    public boolean deleteMenu(MenuDto menuDto);

}
