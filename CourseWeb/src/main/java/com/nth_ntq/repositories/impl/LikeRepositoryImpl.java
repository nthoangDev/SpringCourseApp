/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.repositories.impl;

import com.nth_ntq.repositories.LikeRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
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
public class LikeRepositoryImpl implements LikeRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public long countLike(Long courseId) {
        Session s = factory.getObject().getCurrentSession();
        Query<Long> q = s.createQuery("SELECT COUNT(*) FROM Likes l WHERE l.courseId.courseId = :cid", Long.class);
        q.setParameter("cid", courseId);
        return q.getSingleResult();
    }
}
