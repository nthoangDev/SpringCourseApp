/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.repositories.impl;

import com.nth_ntq.pojo.Carts;
import com.nth_ntq.repositories.CartRepository;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author pc
 */
@Repository
@Transactional
public class CartRepositoryImpl implements CartRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

//    @Override
//    public Carts findByUserId(Long userId) {
//        Session s = factory.getObject().getCurrentSession();
//        CriteriaBuilder cb = s.getCriteriaBuilder();
//        CriteriaQuery<Carts> cq = cb.createQuery(Carts.class);
//        Root<Carts> root = cq.from(Carts.class);
//        cq.select(root).where(cb.equal(root.get("userId").get("userId"), userId));
//        return s.createQuery(cq).uniqueResult();
//    }
    @Override
    public Carts findByUserId(Long userId) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Carts> cq = cb.createQuery(Carts.class);
        Root<Carts> root = cq.from(Carts.class);

        // Fetch cartItemsSet để tránh LazyInitializationException
        root.fetch("cartItemsSet", JoinType.LEFT);

        cq.select(root).where(cb.equal(root.get("userId").get("userId"), userId));
        return s.createQuery(cq).uniqueResult();
    }

    @Override
    public void save(Carts cart) {
        Session s = factory.getObject().getCurrentSession();

        s.merge(cart);

    }
}
