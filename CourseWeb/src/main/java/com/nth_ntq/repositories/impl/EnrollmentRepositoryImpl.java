/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.repositories.impl;

import com.nth_ntq.pojo.Enrollments;
import com.nth_ntq.repositories.EnrollmentRepository;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Autowired
    public EnrollmentRepositoryImpl(LocalSessionFactoryBean factory
    ) {
        this.factory = factory;

    }

    @Override
    public long countByCourse(Long courseId
    ) {
        Session s = factory.getObject().getCurrentSession();
        Query<Long> q = s.createQuery("SELECT COUNT(*) FROM Enrollments e WHERE e.courseId.courseId = :cid", Long.class);
        q.setParameter("cid", courseId);
        return q.getSingleResult();
    }

    @Override
    public boolean existsByUserAndCourse(Long userId, Long courseId
    ) {
        Session s = factory.getObject().getCurrentSession();
        Query<Long> q = s.createQuery("SELECT COUNT(*) FROM Enrollments e WHERE e.userId.userId = :uid AND e.course.courseId = :cid", Long.class);
        q.setParameter("uid", userId);
        q.setParameter("cid", courseId);
        return q.getSingleResult() > 0;
    }

    @Override
    public List<Enrollments> findByUserId(Long userId
    ) {
        Session s = factory.getObject().getCurrentSession();
        // Chú ý: property trong Enrollments là 'userId', không phải 'users'
        TypedQuery<Enrollments> q = s.createQuery(
                "SELECT e FROM Enrollments e WHERE e.userId.userId = :uid",
                Enrollments.class
        );
        q.setParameter("uid", userId);
        return q.getResultList();
    }

    @Override
    public Optional<Enrollments> findByUserIdAndCourseId(Long userId, Long courseId
    ) {
        Session session = factory.getObject().getCurrentSession();
        Query q = session.createQuery(
                "FROM Enrollments e "
                + " WHERE e.userId.userId = :uid AND e.courseId.courseId = :cid",
                Enrollments.class
        );
        q.setParameter("uid", userId);
        q.setParameter("cid", courseId);
        List<Enrollments> list = q.getResultList();
        if (list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(list.get(0));
    }
}
