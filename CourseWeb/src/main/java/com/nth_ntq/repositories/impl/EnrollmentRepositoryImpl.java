/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.repositories.impl;

import com.nth_ntq.pojo.Enrollments;
import com.nth_ntq.repositories.EnrollmentRepository;
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
public class EnrollmentRepositoryImpl implements EnrollmentRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Enrollments save(Enrollments e) {
        Session s = factory.getObject().getCurrentSession();
        s.persist(e);
        return e;
    }

    @Override
    public long countByCourse(Long courseId) {
        Session s = factory.getObject().getCurrentSession();
        Query<Long> q = s.createQuery("SELECT COUNT(*) FROM Enrollments e WHERE e.courseId.courseId = :cid", Long.class);
        q.setParameter("cid", courseId);
        return q.getSingleResult();
    }

    @Override
    public boolean existsByUserAndCourse(Long userId, Long courseId) {
        Session s = factory.getObject().getCurrentSession();
        Query<Long> q = s.createQuery("SELECT COUNT(*) FROM Enrollments e WHERE e.userId.userId = :uid AND e.course.courseId = :cid", Long.class);
        q.setParameter("uid", userId);
        q.setParameter("cid", courseId);
        return q.getSingleResult() > 0;
    }
}
