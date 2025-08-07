/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.repositories.impl;

import com.nth_ntq.pojo.CourseTeachers;
import com.nth_ntq.pojo.Courses;
import com.nth_ntq.pojo.Tags;
import com.nth_ntq.pojo.Users;
import com.nth_ntq.repositories.CourseRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
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
import java.util.NoSuchElementException;
import org.hibernate.Hibernate;

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
            // Lọc theo level
            String level = params.get("level");
            if (level != null && !level.isEmpty()) {
                predicates.add(b.equal(root.get("level"), level));
            }

            // Sắp xếp (nếu có)
            String orderBy = params.get("orderBy");
            if ("price_desc".equals(orderBy)) {
                q.orderBy(b.desc(root.get("price")));
            } else if ("price_asc".equals(orderBy)) {
                q.orderBy(b.asc(root.get("price")));
            } else {
                q.orderBy(b.desc(root.get("createdAt"))); // mặc định
            }

            String tagId = params.get("tagId");
            if (tagId != null && !tagId.isEmpty()) {
                Join<Courses, Tags> tagJoin = root.join("tagsSet");
                predicates.add(b.equal(tagJoin.get("tagId"), Long.parseLong(tagId)));
            }

            q.where(predicates.toArray(Predicate[]::new));
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
        Courses c = s.get(Courses.class, id);
        if (c == null) {
            throw new NoSuchElementException("Khóa học không tồn tại: " + id);
        }
        // Force-load các relation cần dùng ở view/service
        Hibernate.initialize(c.getTagsSet());
        Hibernate.initialize(c.getCourseTeachersSet());
        return c;
    }

    @Override
    public Courses addOrUpdateCourse(Courses c) {
        Session s = this.factory.getObject().getCurrentSession();
        if (c.getCourseId() == null) {
            s.persist(c);
        } else {
            s.merge(c);
        }
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

    @Override
    public List<Courses> findCoursesByTeacherUsername(String username) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Courses> cq = cb.createQuery(Courses.class);

        Root<CourseTeachers> root = cq.from(CourseTeachers.class);
        Join<CourseTeachers, Courses> courseJoin = root.join("courseId");
        Join<CourseTeachers, Users> teacherJoin = root.join("userId");

        cq.select(courseJoin)
                .where(cb.equal(teacherJoin.get("username"), username))
                .distinct(true);

        return s.createQuery(cq).getResultList();
    }

}
