/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.repositories.impl;

import com.nth_ntq.pojo.CourseTeachers;
import com.nth_ntq.repositories.CourseTeachersRepository;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author trung
 */
@Repository
@Transactional
public class CourseTeachersRepositoryImpl implements CourseTeachersRepository {
    private final LocalSessionFactoryBean factory;

    @Autowired
    public CourseTeachersRepositoryImpl(LocalSessionFactoryBean factory) {
        this.factory = factory;
    }

    @Override
    public CourseTeachers add(CourseTeachers ct) {
        Session session = factory.getObject().getCurrentSession();
        session.persist(ct);
        return ct;
    }

    @Override
    public List<CourseTeachers> findByCourseId(Long courseId) {
        Session session = factory.getObject().getCurrentSession();
        TypedQuery<CourseTeachers> q = session.createNamedQuery(
            "CourseTeachers.findAll", CourseTeachers.class
        );
        // filter in memory or write a dedicated NamedQuery
        List<CourseTeachers> list = q.getResultList();
        return list.stream()
            .filter(ct -> ct.getCourseId().getCourseId().equals(courseId))
            .toList();
    }
}