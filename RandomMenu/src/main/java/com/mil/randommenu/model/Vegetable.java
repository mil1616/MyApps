/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mil.randommenu.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Gael Sonney
 */
@Entity
@Table(name = "vegetable")
public class Vegetable extends Item implements Serializable, Comparable<Vegetable> {

    @Override
    public int compareTo(Vegetable o) {
        return this.getName().compareTo(o.getName());
    }

}
