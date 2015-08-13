/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mil.randommenu.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Gael Sonney
 */
@Entity
@Table(name = "MENU_ITEM")
public class MenuItem extends Item implements Serializable, Comparable<MenuItem> {

    @ManyToOne(fetch = FetchType.EAGER)
    private Menu menu;

    @ManyToOne(fetch = FetchType.EAGER)
    private Vegetable vegetable;

    @Column(name = "quantity")
    private Integer quantity;

    private Integer fridgeQuantity;

//    private Integer calcQuantity;
//
//    public Integer getCalcQuantity() {
//        return quantity - fridgeQuantity;
//    }
//    public void setCalcQuantity(Integer calcQuantity) {
////        this.calcQuantity = calcQuantity;
//    }
    public Integer getFridgeQuantity() {
        return fridgeQuantity;
    }

    public void setFridgeQuantity(Integer fridgeQuantity) {
        this.fridgeQuantity = fridgeQuantity;
    }

    public static MenuItem getInstanceMenuItem(Menu menu, Vegetable vegetable) {
        MenuItem menuItem = new MenuItem();
        menuItem.setMenu(menu);
        menuItem.setvegetable(vegetable);
        return menuItem;
    }

    public static MenuItem getInstanceMenuItem(Vegetable vegetable, Integer quantity) {
        MenuItem menuItem = new MenuItem();
        menuItem.setvegetable(vegetable);
        menuItem.setQuantity(quantity);
        return menuItem;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Vegetable getVegetable() {
        return vegetable;
    }

    public void setvegetable(Vegetable vegetable) {
        this.vegetable = vegetable;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void addQuantity(Integer quantity) {
        this.quantity += quantity;
    }

    public void substractQuantity(Integer quantity) {
        this.quantity -= quantity;
    }

    public void addFridgeQuantity(Integer quantity) {
        this.fridgeQuantity += quantity;
    }

    public void substractFridgeQuantity(Integer quantity) {
        this.fridgeQuantity -= quantity;
    }

    public Integer getCalculatedQuantity() {
        if (fridgeQuantity != null) {
            return quantity - fridgeQuantity;
        }
        return quantity;
    }

    @Override
    public int compareTo(MenuItem o) {
        return this.getVegetable().getName().compareTo(o.getVegetable().getName());
    }
}
