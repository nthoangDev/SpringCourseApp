/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.repositories.impl;

import com.nth_ntq.pojo.Comments;
import com.nth_ntq.repositories.CommentRepository;
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
public class CommentRepositoryImpl implements CommentRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Comments> getCommentsByCourse(Long courseId) {
        Session s = factory.getObject().getCurrentSession();
        Query<Comments> q = s.createQuery(
            "FROM Comments c WHERE c.courseId.courseId = :cid ORDER BY c.createdAt DESC", Comments.class);
        q.setParameter("cid", courseId);
        return q.getResultList();
    }
}
