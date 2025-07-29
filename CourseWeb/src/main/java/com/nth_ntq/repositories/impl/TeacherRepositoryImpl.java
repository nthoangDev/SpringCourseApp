/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.repositories.impl;

import com.nth_ntq.pojo.Users;
import com.nth_ntq.repositories.TeacherRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class TeacherRepositoryImpl implements TeacherRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Users> getTeachers() {
        Session s = factory.getObject().getCurrentSession();
        Query<Users> q = s.createQuery(
                "FROM Users u WHERE u.role = :role", Users.class
        );
        q.setParameter("role", "TEACHER");
        return q.getResultList();
    }

    @Override
    public List<Users> getUsersByRole(String role) {
        Session s = factory.getObject().getCurrentSession();
        Query<Users> q = s.createQuery(
                "FROM Users u WHERE u.role = :role", Users.class
        );
        q.setParameter("role", role);
        return q.getResultList();
    }

    @Override
    public Users getTeacherById(Long id) {
        Session s = factory.getObject().getCurrentSession();
        return s.get(Users.class, id);
    }

    @Override
    public void addOrUpdateTeacher(Users u) {
        Session s = factory.getObject().getCurrentSession();
        if (u.getUserId() == null) {
            s.persist(u);
        } else {
            s.merge(u);
        }
    }

    @Override
    public void deleteTeacher(Long id) {
        Session s = factory.getObject().getCurrentSession();
        Users u = s.get(Users.class, id);
        if (u != null) {
            s.remove(u);
        }
    }
    
    @Override
    public List<Users> getTeachersByKeyword(String kw) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Users> cq = cb.createQuery(Users.class);
        Root<Users> root = cq.from(Users.class);

        // u.role = 'TEACHER'
        Predicate pRole = cb.equal(root.get("role"), "TEACHER");
        if (kw != null && !kw.isBlank()) {
            String pattern = "%" + kw.trim() + "%";
            Predicate pName = cb.like(root.get("username"), pattern);
            Predicate pFull = cb.like(root.get("fullName"), pattern);
            cq.where(cb.and(pRole, cb.or(pName, pFull)));
        } else {
            cq.where(pRole);
        }

        cq.orderBy(cb.asc(root.get("username")));
        return s.createQuery(cq).getResultList();
    }
}
