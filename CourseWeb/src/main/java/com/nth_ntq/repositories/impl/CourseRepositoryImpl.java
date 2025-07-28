/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.repositories.impl;

import com.nth_ntq.pojo.Courses;
import com.nth_ntq.repositories.CourseRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 *
 * @author pc
 */
@Repository
@Transactional
public class CourseRepositoryImpl implements CourseRepository {
    private static final int PAGE_SIZE = 6;
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Courses> getCourses(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Courses> q = b.createQuery(Courses.class);
        Root<Courses> root = q.from(Courses.class);
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("title"), "%" + kw + "%"));
            }

            String id = params.get("id");
            if (id != null && !id.isEmpty()) {
                predicates.add(b.equal(root.get("id").as(Long.class), Long.valueOf(id)));
            }

            // Add more filters if needed: status, level, category...
            q.where(predicates.toArray(Predicate[]::new));

            String orderBy = params.get("orderBy");
            if (orderBy != null && !orderBy.isEmpty()) {
                q.orderBy(b.asc(root.get(orderBy)));
            }
        }

        Query query = s.createQuery(q);

        if (params != null && params.containsKey("page")) {
            int page = Integer.parseInt(params.get("page"));
            int start = (page - 1) * PAGE_SIZE;

            query.setMaxResults(PAGE_SIZE);
            query.setFirstResult(start);
        }

        return query.getResultList();
    }

    @Override
    public Courses getCourseById(Long id) {
        Session s = factory.getObject().getCurrentSession();
        return s.get(Courses.class, id);
    }

    @Override
    public Courses addOrUpdateCourse(Courses c) {
        Session s = this.factory.getObject().getCurrentSession();
        if (c.getCourseId() == null)
            s.persist(c);
        else
            s.merge(c);
        return c;
    }

    @Override
    public void deleteCourse(Long id) {
        Session s = factory.getObject().getCurrentSession();
        Courses c = s.get(Courses.class, id);
        if (c != null) {
            s.remove(c);
        }
    }

    @Override
    public long countCourse() {
        Session s = factory.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT COUNT(*) FROM Courses", Courses.class);
        return (long) q.getSingleResult();
    }

}
