/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.repositories.impl;

import com.nth_ntq.repositories.CartItemRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

/**
 *
 * @author pc
 */
public class CartItemRepositoryImpl implements CartItemRepository{

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public boolean existsByUserAndCourse(Long userId, Long courseId) {

        Session s = factory.getObject().getCurrentSession();
        Query<Long> q = s.createQuery("SELECT COUNT(*) FROM CartItem c WHERE c.user.userId = :uid AND c.course.courseId = :cid", Long.class);
        q.setParameter("uid", userId);
        q.setParameter("cid", courseId);
        return q.getSingleResult() > 0;
    }

}
