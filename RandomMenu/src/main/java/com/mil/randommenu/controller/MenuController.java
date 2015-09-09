/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mil.randommenu.controller;

import com.mil.randommenu.model.Menu;
import com.mil.randommenu.model.Vegetable;
import com.mil.randommenu.model.WeekItem;
import com.mil.randommenu.service.MenuService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Gael Sonney
 */
@Controller
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    private List<WeekItem> weekItems;

    public List<WeekItem> getWeekItems() {
        return weekItems;
    }

    public void setWeeksItems(List<WeekItem> weeksMenuItems) {
        this.weekItems = weeksMenuItems;
    }

    public MenuService getMenuService() {
        return menuService;
    }

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewMenus(@ModelAttribute("menu") Menu menu) {
        Map model = new HashMap();
        model.put("menus", menuService.getNotWeekMenus());
        model.put("weekMenus", menuService.getWeekMenus());
        model.put("weekItems", menuService.getWeekItems());
        return new ModelAndView("menusView", model);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView editMenu(@RequestParam("menuId") String menuId) {
        Map model = new HashMap();
        Menu menu = menuService.getMenu(Long.valueOf(menuId));

        model.put("menu", menu);
        model.put("menuItems", menuService.getMenuItemsFromMenu(menu.getId()));
        model.put("menuvegetables", menuService.getVegetablesFromMenu(menu.getId()));
        model.put("vegetables", menuService.getAllVegetablesNotInMenu(menu.getId()));
        model.put("vegetable", new Vegetable());
        return new ModelAndView("addMenu", model);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addMenu(@ModelAttribute("menu") Menu menu, BindingResult result) {
        menuService.addMenu(menu);
        Map model = new HashMap();
        model.put("menu", menu);
        model.put("menuvegetables", menuService.getVegetablesFromMenu(menu.getId()));
        model.put("vegetables", menuService.getAllVegetables());
        model.put("vegetable", new Vegetable());
//        return new ModelAndView("addMenu", model);
        return "redirect:/menus/" + menu.getId();

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView editMenu(@PathVariable("id") int id, Model model) {
//        ControllerUtil.logBindingErrors ( result );
        Menu menu = menuService.getMenu(id);
        model.addAttribute("menu", menu);
        model.addAttribute("menuvegetables", menuService.getVegetablesFromMenu(menu.getId()));
        model.addAttribute("menuItems", menuService.getMenuItemsFromMenu(menu.getId()));
        model.addAttribute("vegetables", menuService.getAllVegetablesNotInMenu(menu.getId()));
        model.addAttribute("vegetable", new Vegetable());
        return new ModelAndView("addMenu", model.asMap());
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String updateMenu(@PathVariable("id") int id, @ModelAttribute("menu") Menu menu, Model model) {
//        ControllerUtil.logBindingErrors ( result );
        menuService.saveMenu(menu);
        model.addAttribute("menu", menu);
        model.addAttribute("menuvegetables", menuService.getVegetablesFromMenu(menu.getId()));
        model.addAttribute("vegetables", menuService.getAllVegetables());
        model.addAttribute("vegetable", new Vegetable());
        return "redirect:/menus/" + menu.getId();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView deleteMenu(@RequestParam("menuId") String menuId) {
//        ControllerUtil.logBindingErrors ( result );
        menuService.deleteMenu(Long.valueOf(menuId));
        return new ModelAndView("redirect:/menus.html");
    }

    @RequestMapping(value = "/addToMenu", method = RequestMethod.POST)
    public ModelAndView addToMenu(@RequestParam("menuId") String menuId, @RequestParam("vegetableId") String vegetableId) {
//        ControllerUtil.logBindingErrors ( result );
        menuService.addToMenu(Long.valueOf(menuId), Long.valueOf(vegetableId));
        Menu menu = menuService.getMenu(Long.valueOf(menuId));
        Map model = new HashMap();
        model.put("menu", menu);
        model.put("menuItems", menuService.getMenuItemsFromMenu(menu.getId()));
        model.put("vegetables", menuService.getAllVegetablesNotInMenu(menu.getId()));
        model.put("vegetable", new Vegetable());
        return new ModelAndView("addMenu", model);
    }

    @RequestMapping(value = "/removeFromMenu", method = RequestMethod.POST)
    public ModelAndView removeFromMenu(@RequestParam("menuId") String menuId, @RequestParam("menuItemId") String menuItemId) {
//        ControllerUtil.logBindingErrors ( result );
        menuService.removeFromMenu(Long.valueOf(menuId), Long.valueOf(menuItemId));
        Menu menu = menuService.getMenu(Long.valueOf(menuId));
        Map model = new HashMap();
        model.put("menu", menu);
        model.put("menuItems", menuService.getMenuItemsFromMenu(menu.getId()));
        model.put("vegetables", menuService.getAllVegetablesNotInMenu(menu.getId()));
        model.put("vegetable", new Vegetable());
        return new ModelAndView("addMenu", model);
    }

    @RequestMapping(value = "/saveQuantity", method = RequestMethod.POST)
    public ModelAndView saveQuantity(@RequestParam("menuQuantity") String menuQuantity, @RequestParam("menuItemId") String menuItemId) {
//        ControllerUtil.logBindingErrors ( result );
        menuService.saveQuantity(Integer.valueOf(menuQuantity), menuItemId);
        Menu menu = menuService.getMenuFromMenuItemId(menuItemId);
        Map model = new HashMap();
        model.put("menu", menu);
        model.put("menuItems", menuService.getMenuItemsFromMenu(menu.getId()));
        model.put("vegetables", menuService.getAllVegetablesNotInMenu(menu.getId()));
        model.put("vegetable", new Vegetable());
        return new ModelAndView("addMenu", model);
    }

    @RequestMapping(value = "/addMenuToWeek", method = RequestMethod.POST)
    public ModelAndView addMenuToWeek(@RequestParam("menuId") String menuId) {
//        ControllerUtil.logBindingErrors ( result );
        menuService.addMenuToWeek(Long.valueOf(menuId));
        Map model = new HashMap();
        model.put("menus", menuService.getNotWeekMenus());
        model.put("weekMenus", menuService.getWeekMenus());
        model.put("weekItems", menuService.getWeekItems());
        return new ModelAndView("redirect:/menus.html", model);
    }

    @RequestMapping(value = "/removeMenuFromWeek", method = RequestMethod.POST)
    public ModelAndView removeMenuFromWeek(@RequestParam("menuId") String menuId) {
//        ControllerUtil.logBindingErrors ( result );
        menuService.removeMenuFromWeek(Long.valueOf(menuId));
        Map model = new HashMap();
        model.put("menus", menuService.getNotWeekMenus());
        model.put("weekMenus", menuService.getWeekMenus());
        model.put("weekItems", menuService.getWeekItems());
        return new ModelAndView("redirect:/menus.html", model);
    }

    @RequestMapping(value = "/saveQuantityInFridge", method = RequestMethod.POST)
    public ModelAndView saveQuantityInFridge(@RequestParam("weekItemName") String weekItemName, @RequestParam("quantity") String quantity, Model modelssss) {
//        ControllerUtil.logBindingErrors ( result );
        menuService.saveQuantityInFridge(!quantity.isEmpty() ? Integer.valueOf(quantity) : null, weekItemName);
        Map model = new HashMap();
        model.put("menus", menuService.getNotWeekMenus());
        model.put("weekMenus", menuService.getWeekMenus());
        model.put("weekItems", menuService.getWeekItems());
        return new ModelAndView("redirect:/menus.html", model);
    }

    @RequestMapping(value = "/sendMail", method = RequestMethod.GET)
    public ModelAndView sendMail() {
//        ControllerUtil.logBindingErrors ( result );
        menuService.sendMail();
        return new ModelAndView("redirect:/menus.html");
    }

//    @RequestMapping(value = "/{id}/addv", method = RequestMethod.POST)
//    public String addVegetable(@ModelAttribute("vegetable") Vegetable vegetable, @PathVariable("id") int id, Model model) {
//        menuService.addVegetable(vegetable);
//        return "redirect:/menus/" + String.valueOf(id);
//    }
}
