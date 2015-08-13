/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mil.randommenu.service;

import com.mil.randommenu.dao.MenuDao;
import com.mil.randommenu.dao.MenuItemDao;
import com.mil.randommenu.dao.VegetableDao;
import com.mil.randommenu.dao.WeekItemDao;
import com.mil.randommenu.model.Menu;
import com.mil.randommenu.model.MenuItem;
import com.mil.randommenu.model.Vegetable;
import com.mil.randommenu.model.WeekItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Gael Sonney
 */
@Service("menuService")
@Transactional(propagation = Propagation.SUPPORTS)
public class MenuService {

    @Autowired
    private MenuDao menuDao;
    @Autowired
    private VegetableDao vegetableDao;
    @Autowired
    private WeekItemDao weekItemDao;
    @Autowired
    private MenuItemDao menuItemDao;

    public MenuService() {
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addMenu(Menu menu) {
        menu.setIsWeekMenu(false);
        menuDao.saveMenu(menu);
    }

    public Menu getMenu(long menuId) {
        return menuDao.getById(menuId);
    }

    public Menu getMenuFromMenuItemId(String menuItemId) {
        return menuDao.getById(menuItemDao.getById(Long.valueOf(menuItemId)).getMenu().getId());
    }

    public List<Menu> getAllMenus() {
        return menuDao.getAll();
    }

    @Transactional(readOnly = false)
    public void deleteMenu(long menuId) {
        menuDao.delete(menuId);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addVegetable(Vegetable vegetable) {
        vegetableDao.saveVegetable(vegetable);
    }

    public List<Vegetable> getAllVegetables() {
        return vegetableDao.getAll();
    }

    public List<MenuItem> getMenuItemsFromMenu(Long menuId) {
        if (menuId != null) {
            return menuItemDao.getMenuItemFromMenu(getMenu(menuId));
        }
        return new ArrayList<>();
    }

    public List<Vegetable> getVegetablesFromMenu(Long menuId) {
        if (menuId != null) {
            return menuItemDao.getVegetablesFromMenu(getMenu(menuId));
        }
        return new ArrayList<>();
    }

    public List<Vegetable> getAllVegetablesNotInMenu(String menuId) {

        List<Vegetable> vedgeList = vegetableDao.getAll();

        if (!menuId.isEmpty()) {
            List<Vegetable> list = getVegetablesFromMenu(Long.valueOf(menuId));
            vedgeList.removeAll(list);
        }
        return vedgeList;
    }

    @Transactional(readOnly = false)
    public void deleteVegetable(long vegetableId) {
        vegetableDao.delete(vegetableId);
    }

    @Transactional(readOnly = false)
    public void addToMenu(long menuId, long vegetableId) {
        menuItemDao.saveMenuItem(MenuItem.getInstanceMenuItem(menuDao.getById(menuId), vegetableDao.getById(vegetableId)));
    }

    @Transactional(readOnly = false)
    public void removeFromMenu(long menuId, long menuItemId) {
        menuItemDao.delete(menuItemId);
    }

    public List<Menu> getWeekMenus() {
        return menuDao.getByIsWeekMenu(true);
    }

    public List<Menu> getNotWeekMenus() {
        return menuDao.getByIsWeekMenu(false);
    }

    @Transactional(readOnly = false)
    public void addMenuToWeek(long menuId) {
        Menu menu = getMenu(menuId);
        menu.setIsWeekMenu(true);
        menuDao.saveMenu(menu);

        for (MenuItem menuItem : getMenuItemsFromMenu(menuId)) {
            addOrUpdateWeekItem(menuItem);
        }
    }

    public void addOrUpdateWeekItem(MenuItem menuItem) {
        WeekItem weekItem = weekItemDao.getWeekItemFromVegetable(menuItem.getVegetable());
        if (weekItem != null) {
            weekItem.addQuantity(menuItem.getQuantity());
        } else {
            weekItemDao.saveWeekItem(WeekItem.getInstance(menuItem.getVegetable(), menuItem.getQuantity()));
        }
    }

    @Transactional(readOnly = false)
    public void removeMenuFromWeek(long menuId) {
        Menu menu = getMenu(menuId);
        menu.setIsWeekMenu(false);
        menuDao.saveMenu(menu);

        for (MenuItem menuItem : getMenuItemsFromMenu(menuId)) {
            removeOrUpdateWeekItem(menuItem);
        }
    }

    public void removeOrUpdateWeekItem(MenuItem menuItem) {
        WeekItem weekItem = weekItemDao.getWeekItemFromVegetable(menuItem.getVegetable());
        if (weekItem != null) {
            weekItem.removeQuantity(menuItem.getQuantity());
        }
//        else {
//            weekItemDao.saveWeekItem(WeekItem.getInstance(menuItem.getVegetable(), menuItem.getQuantity()));
//        }
    }

    @Transactional(readOnly = false)
    public void saveQuantity(Integer menuQuantity, String menuItemId) {
        MenuItem menuItem = menuItemDao.getById(Long.valueOf(menuItemId));
        menuItem.setQuantity(menuQuantity);
        menuItemDao.saveMenuItem(menuItem);
    }

    @Transactional(readOnly = false)
    public void saveQuantityInFridge(Integer quantity, String weekItemId) {
        WeekItem weekItem = weekItemDao.getById(Long.valueOf(weekItemId));
        weekItem.setFridgeQuantity(quantity);
        weekItemDao.saveWeekItem(weekItem);
    }

    public Integer getQuantityFromVegetable(Vegetable vegetable) {
        Long i = menuItemDao.countVegetableFromWeekMenus(vegetable);
        return (i != null ? i.intValue() : 0);
    }

    public List<WeekItem> getWeekItems() {
        List<WeekItem> weekItems = weekItemDao.getWeekItems();
        Collections.sort(weekItems);
        return weekItems;
    }
}
