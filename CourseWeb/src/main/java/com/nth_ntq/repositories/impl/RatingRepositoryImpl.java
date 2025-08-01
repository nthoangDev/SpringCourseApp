/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.repositories.impl;

import com.nth_ntq.repositories.RatingRepository;
import java.util.HashMap;
import java.util.Map;
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
public class RatingRepositoryImpl implements RatingRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Map<String, Object> getRatingSummary(Long courseId) {
        Session s = factory.getObject().getCurrentSession();
        Query<Object[]> q = s.createQuery(
            "SELECT AVG(r.rating), COUNT(r.rating) FROM Ratings r WHERE r.courseId.courseId = :cid", Object[].class);
        q.setParameter("cid", courseId);

        Object[] result = q.getSingleResult();
        Map<String, Object> summary = new HashMap<>();
        summary.put("average", result[0] != null ? result[0] : 0);
        summary.put("count", ((Long) result[1]));
        return summary;
    }
}

