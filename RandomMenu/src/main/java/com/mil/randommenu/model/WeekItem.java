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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Gael Sonney
 */
@Entity
@Table(name = "WEEK_ITEM")
public class WeekItem extends Item implements Serializable, Comparable<WeekItem> {

    @OneToOne(fetch = FetchType.EAGER)
    private Vegetable vegetable;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "fridgeQuantity")
    private Integer fridgeQuantity;

    public static WeekItem getInstance(Vegetable vegetable, Integer quantity) {
        WeekItem weekItem = new WeekItem();
        weekItem.setVegetable(vegetable);
        weekItem.setQuantity(quantity);
        return weekItem;
    }

    public Vegetable getVegetable() {
        return vegetable;
    }

    public void setVegetable(Vegetable vegetable) {
        this.vegetable = vegetable;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getFridgeQuantity() {
        return fridgeQuantity;
    }

    public void setFridgeQuantity(Integer fridgeQuantity) {
        this.fridgeQuantity = fridgeQuantity;
    }

    public void addQuantity(Integer quantity) {
        if (quantity != null) {
            this.quantity += quantity;
        }
    }

    public void removeQuantity(Integer quantity) {
        if (quantity != null) {
            this.quantity -= quantity;
            if (this.quantity < 0) {
                this.quantity = 0;
            }
        }
    }

    public Integer getQuantityToShop() {
        if (fridgeQuantity != null && quantity - fridgeQuantity > 0) {
            return quantity - fridgeQuantity;
        } else if (fridgeQuantity != null) {
            return 0;
        } else {
            return quantity;
        }
    }

    @Override
    public int compareTo(WeekItem o) {
        return this.getVegetable().getName().compareTo(o.getVegetable().getName());
    }
}
