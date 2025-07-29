/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.repositories.impl;

import com.nth_ntq.pojo.Assessments;
import com.nth_ntq.repositories.AssessmentRepository;
import java.util.List;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
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
}