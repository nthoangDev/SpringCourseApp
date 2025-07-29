/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.repositories.impl;

import com.nth_ntq.pojo.Lessons;
import com.nth_ntq.repositories.LessonRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
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
public class LessonRepositoryImpl implements LessonRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Lessons> getLessonsByCourseId(Long courseId) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Lessons> q = b.createQuery(Lessons.class);
        Root<Lessons> root = q.from(Lessons.class);
        q.select(root);
        q.where(b.equal(root.get("courseId").get("courseId"), courseId));

        return s.createQuery(q).getResultList();
    }

    @Override
    public Lessons getLessonById(Long id) {
        Session s = factory.getObject().getCurrentSession();
        String hql = "SELECT l FROM Lessons l LEFT JOIN FETCH l.assessmentsSet WHERE l.lessonId = :id";
        Query<Lessons> q = s.createQuery(hql, Lessons.class);
        q.setParameter("id", id);
        return q.uniqueResult();

    }

    @Override
    public Lessons addOrUpdateLesson(Lessons l) {
        Session s = factory.getObject().getCurrentSession();
        if (l.getLessonId() == null) {
            s.persist(l);
        } else {
            s.merge(l);
        }
        return l;
    }

    @Override
    public void deleteLesson(Long id) {
        Session s = factory.getObject().getCurrentSession();
        Lessons l = getLessonById(id);
        if (l != null) {
            s.remove(l);
        }
    }
}
