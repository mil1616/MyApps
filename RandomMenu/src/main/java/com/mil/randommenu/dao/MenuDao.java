/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mil.randommenu.dao;

import com.mil.randommenu.model.Menu;
import com.mil.randommenu.model.MenuItem;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gael Sonney
 */
@Repository("menuDao")
public class MenuDao {

    @Autowired
    SessionFactory sessionFactory;

    public void saveMenu(Menu menu) {
        sessionFactory.getCurrentSession().saveOrUpdate(menu);
    }

    public List<Menu> getAll() {
        return (List<Menu>) sessionFactory.getCurrentSession().createCriteria(Menu.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .addOrder(Order.asc("name"))
                .list();
    }

    public Menu getById(long id) {
        return (Menu) sessionFactory.getCurrentSession().get(Menu.class, id);
    }

    public void delete(long menuId) {
        try {
            sessionFactory.getCurrentSession().delete(getById(menuId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<Menu> getByIsWeekMenu(Boolean isWeekMenu){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Menu.class);
        criteria.add(Restrictions.eq("isWeekMenu", isWeekMenu));
        criteria.addOrder(Order.asc("name"));
        return criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }
    
    public List<MenuItem> getMenuItemFromWeekMenus(){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Menu.class);
        criteria.add(Restrictions.eq("isWeekMenu", true));
        criteria.setProjection(Projections.property("vegetables"));
        return criteria.list();
    }
}
