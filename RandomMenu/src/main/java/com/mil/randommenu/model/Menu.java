/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mil.randommenu.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Gael Sonney
 */
@Entity
@Table(name = "MENU")
public class Menu extends Item implements Serializable {

    @OneToMany(fetch = FetchType.EAGER)
    private List<MenuItem> vegetables;

    @Column(name = "isWeekMenu")
    private Boolean isWeekMenu;

    @Column(name = "comment", length = 2000)
    private String comment;

    public List<MenuItem> getvegetables() {
        return vegetables;
    }

    public void setvegetables(List<MenuItem> vegetables) {
        this.vegetables = vegetables;
    }

    public boolean isIsWeekMenu() {
        return isWeekMenu;
    }

    public void setIsWeekMenu(boolean isWeekMenu) {
        this.isWeekMenu = isWeekMenu;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
