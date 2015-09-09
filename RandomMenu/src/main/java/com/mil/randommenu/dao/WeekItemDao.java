/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mil.randommenu.dao;

import com.mil.randommenu.model.Vegetable;
import com.mil.randommenu.model.WeekItem;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gael Sonney
 */
@Repository("weekItemDao")
public class WeekItemDao {

    @Autowired
    SessionFactory sessionFactory;

    public WeekItem getById(long id) {
        return (WeekItem) sessionFactory.getCurrentSession().get(WeekItem.class, id);
    }

    public void saveWeekItem(WeekItem weekItem) {
        sessionFactory.getCurrentSession().saveOrUpdate(weekItem);
    }

    public void delete(long weekItemId) {
        try {
            sessionFactory.getCurrentSession().delete(getById(weekItemId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WeekItem getWeekItemFromVegetable(Vegetable vegetable) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(WeekItem.class);
        criteria.add(Restrictions.eq("vegetable", vegetable));
        return (WeekItem) criteria.uniqueResult();
    }

    public List<WeekItem> getWeekItems() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(WeekItem.class);
//        criteria.add(Restrictions.gt("quantity", 0));
        criteria.add(Restrictions.disjunction(Restrictions.gt("quantity", 0), Restrictions.isNull("quantity")));
        return criteria.list();
    }

}
