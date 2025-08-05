/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.repositories.impl;

import com.nth_ntq.pojo.Assessments;
import com.nth_ntq.pojo.Assignments;
import com.nth_ntq.pojo.Lessons;
import com.nth_ntq.pojo.Tests;
import com.nth_ntq.repositories.AssessmentRepository;
import java.util.List;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
public class AssessmentRepositoryImpl implements AssessmentRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Assessments addOrUpdateAssessment(Assessments a) {
        Session s = factory.getObject().getCurrentSession();
        if (a.getAssessmentId() == null) {
            s.persist(a);
        } else {
            s.merge(a);
        }
        return a;
    }

    @Override
    public List<Assessments> getAssessmentsByCourseId(Long courseId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Assessments> q = b.createQuery(Assessments.class);
        Root<Assessments> root = q.from(Assessments.class);

        Predicate p = b.equal(root.get("courseId").get("courseId"), courseId);
        q.select(root).where(p);

        return s.createQuery(q).getResultList();
    }

    @Override
    public Assessments getById(Long id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Assessments.class, id);
    }

    @Override
    public void deleteAssessment(Long id) {
        Session s = factory.getObject().getCurrentSession();
        Assessments a = s.get(Assessments.class, id);
        if (a != null) {
            s.remove(a);
        }
    }

    @Override
    public void addAssignment(Assignments a) {
        Session session = this.factory.getObject().getCurrentSession();

        if (a.getAssignmentId() != null) {
            session.merge(a);
        } else {
            session.persist(a);
        }
    }

    @Override
    public void addTest(Tests t) {
        Session session = this.factory.getObject().getCurrentSession();

        if (t.getTestId() != null) {
            session.merge(t);
        } else {
            session.persist(t);
        }
    }

    @Override
    public List<Assessments> getAssessmentsByLessonId(Long lessonId) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Assessments> cq = cb.createQuery(Assessments.class);

        Root<Lessons> lessonRoot = cq.from(Lessons.class);
        Join<Lessons, Assessments> join = lessonRoot.join("assessmentsSet");

        cq.select(join)
                .where(cb.equal(lessonRoot.get("lessonId"), lessonId))
                .distinct(true);

        return s.createQuery(cq).getResultList();
    }
}
