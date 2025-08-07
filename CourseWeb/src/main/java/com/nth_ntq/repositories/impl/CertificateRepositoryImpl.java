/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.repositories.impl;

import com.nth_ntq.pojo.Certificates;
import com.nth_ntq.repositories.CertificateRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

/**
 *
 * @author pc
 */
@Repository
public class CertificateRepositoryImpl implements CertificateRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Certificates findByUserAndCourse(Long userId, Long courseId) {
        Session s = factory.getObject().getCurrentSession();
        Query<Certificates> q = s.createQuery("FROM Certificates c WHERE c.userId.userId = :uid AND c.courseId.courseId = :cid", Certificates.class);
        q.setParameter("uid", userId);
        q.setParameter("cid", courseId);
        return q.uniqueResult();
    }

    @Override
    public void save(Certificates cert) {
        Session s = factory.getObject().getCurrentSession();
        s.save(cert);
    }
}

