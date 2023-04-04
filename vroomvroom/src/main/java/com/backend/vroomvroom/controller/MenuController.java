package com.backend.vroomvroom.controller;

import com.backend.vroomvroom.dto.menu.request.CreateMenuDto;
import com.backend.vroomvroom.dto.menu.request.UpdateMenuDto;
import com.backend.vroomvroom.dto.menu.response.MenuDto;
import com.backend.vroomvroom.service.menu.IMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public MenuDto createMenu(@RequestBody @Valid CreateMenuDto createMenuDto) throws Exception {
        log.info("createMenu register, request : {}", createMenuDto);
        log.info("createMenu register, userId : {}", createMenuDto.getMenuId());

        return menuService.createMenu(createMenuDto);
    }

    /**
     * 메뉴 수정
     * @param updateMenuDto
     * @return
     * @throws Exception
     */
    @PatchMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public MenuDto updateMenu(@RequestBody @Valid UpdateMenuDto updateMenuDto) throws Exception {
        log.info("updateMenu register, request : {}", updateMenuDto);
        log.info("updateMenu register, userId : {}", updateMenuDto.getMenuId());

        return menuService.updateMenu(updateMenuDto);
    }

    /**
     * 메뉴 상세 정보
     * @param menuId
     * @return
     */
    @GetMapping("/detail/{menuId}")
    @ResponseStatus(HttpStatus.OK)
    public MenuDto detailMenu(@PathVariable("menuId") String menuId) {
        log.info("menuDetail register, request : {}", menuId);
        return menuService.getDetailMenu(menuId);
    }

    /**
     * 메뉴 목록
     * @return
     */
    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<MenuDto> menuList() {
        log.info("menuList By UseYn register");
        return menuService.list();
    }

    /**
     * 메뉴 목록 (사용 여부 관계 없이)
     * @return
     */
    @GetMapping("/list/all")
    @ResponseStatus(HttpStatus.OK)
    public List<MenuDto> menuAllList() {
        log.info("menuList all register");
        return menuService.listAll();
    }

    /**
     * 메뉴 삭제
     * @param menuId
     * @return
     * @throws Exception
     */
    @PatchMapping("/delete/{menuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Boolean deleteMenu(@PathVariable("menuId") String menuId) throws Exception {
        log.info("deleteMenu register, request : {}", menuId);
        return menuService.deleteMenu(menuId);
    }
}
