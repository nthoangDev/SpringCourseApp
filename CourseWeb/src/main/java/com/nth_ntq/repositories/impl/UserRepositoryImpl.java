/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.repositories.impl;

import com.nth_ntq.pojo.Courses;
import com.nth_ntq.pojo.Enrollments;
import com.nth_ntq.pojo.Users;
import com.nth_ntq.repositories.UserRepository;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author trung
 */
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserRepositoryImpl(LocalSessionFactoryBean factory) {
        this.factory = factory;
    }

    @Override
    public List<Users> getUsersByKeyword(String kw) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Users> q = b.createQuery(Users.class);
        Root<Users> root = q.from(Users.class);
        q.select(root);

        if (kw != null && !kw.isEmpty()) {
            String pattern = String.format("%%%s%%", kw);
            Predicate p1 = b.like(root.get("username"), pattern);
            Predicate p2 = b.like(root.get("fullName"), pattern);
            q.where(b.or(p1, p2));
        }

        Query<Users> query = s.createQuery(q);
        return query.getResultList();

    }

    @Override
    public Users getUserByUsername(String username) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("Users.findByUsername", Users.class);
        q.setParameter("username", username);

        return (Users) q.getSingleResult();

    }

    @Override
    public Users addUser(Users u) {
        Session s = this.factory.getObject().getCurrentSession();
        s.persist(u);

        return u;
    }

    @Override
    public boolean authenticate(String username, String password) {
        Users u = this.getUserByUsername(username);

        return this.passwordEncoder.matches(password, u.getPassword());
    }

    @Override
    public Users getUserById(Long userId) {
        Session s = factory.getObject().getCurrentSession();
        Users u = s.get(Users.class, userId);
        return u;
    }

    public Users findById(Long id) {
        Session s = factory.getObject().getCurrentSession();
        // s.get() trả về null nếu không tìm thấy
        return s.get(Users.class, id);
    }

    @Override
    public List<Users> findByRole(String role) {
        Session s = factory.getObject().getCurrentSession();
        TypedQuery<Users> q = s.createNamedQuery(
                "Users.findByRole", Users.class
        );
        q.setParameter("role", role);
        return q.getResultList();
    }

    @Override
    public List<Users> getStudentsByCourseId(Long courseId) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Users> cq = cb.createQuery(Users.class);

        Root<Enrollments> root = cq.from(Enrollments.class);
        Join<Enrollments, Users> userJoin = root.join("userId");
        Join<Enrollments, Courses> courseJoin = root.join("courseId");

        cq.select(userJoin).where(cb.equal(courseJoin.get("courseId"), courseId)).distinct(true);

        return s.createQuery(cq).getResultList();
    }
}
