/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mil.randommenu.dao;

import com.mil.randommenu.model.Vegetable;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gael Sonney
 */
@Repository("vegetableDao")
public class VegetableDao {

    @Autowired
    SessionFactory sessionFactory;

    public void saveVegetable(Vegetable vegetable) {
        sessionFactory.getCurrentSession().saveOrUpdate(vegetable);
    }

    public List<Vegetable> getAll() {
        return (List<Vegetable>) sessionFactory.getCurrentSession().createCriteria(Vegetable.class).list();
    }

//    public List<vegetable> getAllNotInMenu(String[] vedgeIdList) {
////        Criteria menuCriteria = sessionFactory.getCurrentSession().createCriteria(Menu.class);
////        menuCriteria.add(Restrictions.eq("menuId", menuId));
//////        menuCriteria.setProjection(Projections.property("id"));
////        menuCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
////        List list = menuCriteria.list();
//        
//        Criteria vegetableCriteria = sessionFactory.getCurrentSession().createCriteria(vegetable.class);
//        vegetableCriteria.add(Restrictions.not(Restrictions.in("id", vedgeIdList)));
//        return (List<vegetable>) vegetableCriteria.list();
//    }
//    public List<vegetable> getAllNotInMenu(List<vegetable> vedgeIdList) {
////        Criteria menuCriteria = sessionFactory.getCurrentSession().createCriteria(Menu.class);
////        menuCriteria.add(Restrictions.eq("menuId", menuId));
//////        menuCriteria.setProjection(Projections.property("id"));
////        menuCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
////        List list = menuCriteria.list();
//        
//        Criteria vegetableCriteria = sessionFactory.getCurrentSession().createCriteria(vegetable.class);
//        vegetableCriteria.add(Restrictions.not(Restrictions.in(vegetable.class, vedgeIdList)));
//        return (List<vegetable>) vegetableCriteria.list();
//    }

    public Vegetable getById(long id) {
        return (Vegetable) sessionFactory.getCurrentSession().get(Vegetable.class, id);
    }

    public void delete(long vegetableId) {
        try {
            sessionFactory.getCurrentSession().delete(getById(vegetableId));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
