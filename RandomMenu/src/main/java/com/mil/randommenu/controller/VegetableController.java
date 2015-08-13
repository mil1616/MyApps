/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mil.randommenu.controller;

import com.mil.randommenu.model.Vegetable;
import com.mil.randommenu.service.MenuService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Gael Sonney
 */
@Controller
@RequestMapping("/vegetables")
public class VegetableController {

    @Autowired
    private MenuService menuService;

    public MenuService getMenuService() {
        return menuService;
    }

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addVegetable(@ModelAttribute("vegetable") Vegetable vegetable) {
        Map model = new HashMap();
        model.put("vegetables", menuService.getAllVegetables());

        return new ModelAndView("addVegetable", model);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView saveVegetable(@ModelAttribute("vegetable") Vegetable vegetable, BindingResult result) {
//        ControllerUtil.logBindingErrors ( result );
        menuService.addVegetable(vegetable);
        return new ModelAndView("redirect:/menus/add.html");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView deletevegetable(@RequestParam("vegetableId") String vegetableId) {
//        ControllerUtil.logBindingErrors ( result );
        menuService.deleteVegetable(Long.valueOf(vegetableId));
        return new ModelAndView("redirect:/menus/add.html");
    }

}
