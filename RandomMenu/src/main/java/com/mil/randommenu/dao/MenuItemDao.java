/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mil.randommenu.dao;

import com.mil.randommenu.model.Menu;
import com.mil.randommenu.model.MenuItem;
import com.mil.randommenu.model.Vegetable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gael Sonney
 */
@Repository("menuItemDao")
public class MenuItemDao {

    @Autowired
    SessionFactory sessionFactory;

    public MenuItem getById(long id) {
        return (MenuItem) sessionFactory.getCurrentSession().get(MenuItem.class, id);
    }

    public void saveMenuItem(MenuItem menuItem) {
        sessionFactory.getCurrentSession().saveOrUpdate(menuItem);
    }

    public void delete(long menuItemId) {
        try {
            sessionFactory.getCurrentSession().delete(getById(menuItemId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<MenuItem> getMenuItemFromMenu(Menu menu) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MenuItem.class);
        criteria.add(Restrictions.eq("menu", menu));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    public List<Vegetable> getVegetablesFromMenu(Menu menu) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MenuItem.class);
        criteria.add(Restrictions.eq("menu", menu));
        criteria.setProjection(Projections.property("vegetable"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    public List<MenuItem> getMenuItemFromWeekMenus() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MenuItem.class);
        Criteria menuCriteria = criteria.createCriteria("menu");
        menuCriteria.add(Restrictions.eq("isWeekMenu", true));
        return criteria.list();
    }

    public List<Vegetable> getVegdetablesFromWeekMenus() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MenuItem.class);
        Criteria menuCriteria = criteria.createCriteria("menu");
        menuCriteria.add(Restrictions.eq("isWeekMenu", true));
        criteria.setProjection(Projections.property("vegetable"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    public Long countVegetableFromWeekMenus(Vegetable vegetable) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MenuItem.class);
        Criteria menuCriteria = criteria.createCriteria("menu");
        menuCriteria.add(Restrictions.eq("isWeekMenu", true));
        Criteria vegetableCriteria = criteria.createCriteria("vegetable");
        vegetableCriteria.add(Restrictions.eq("name", vegetable.getName()));
        criteria.setProjection(Projections.sum("quantity"));
        return (Long) criteria.uniqueResult();
    }
}
