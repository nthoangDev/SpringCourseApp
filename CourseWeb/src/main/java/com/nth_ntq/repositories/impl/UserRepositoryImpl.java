/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.repositories.impl;

import com.nth_ntq.pojo.Users;
import com.nth_ntq.repositories.UserRepository;
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
}
