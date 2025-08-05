/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.repositories.impl;

import com.nth_ntq.pojo.AssessmentResults;
import com.nth_ntq.pojo.AssessmentResultsPK;
import com.nth_ntq.repositories.AssessmentResultRepository;
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
public class AssessmentResultRepositoryImpl implements AssessmentResultRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<AssessmentResults> getResultsByAssessment(Long aid) {
        Session s = factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM AssessmentResults WHERE assessments.assessmentId = :aid", AssessmentResults.class);
        q.setParameter("aid", aid);
        return q.getResultList();
    }

    @Override
    public AssessmentResults getResult(Long aid, Long uid) {
        Session s = factory.getObject().getCurrentSession();
        return s.get(AssessmentResults.class, new AssessmentResultsPK(aid, uid));
    }

    @Override
    public AssessmentResults save(AssessmentResults r) {
        Session s = factory.getObject().getCurrentSession();
        if (r.getAssessmentResultsPK() == null) {
            s.persist(r);
        } else {
            s.merge(r);
        }
        return r;
    }
}
