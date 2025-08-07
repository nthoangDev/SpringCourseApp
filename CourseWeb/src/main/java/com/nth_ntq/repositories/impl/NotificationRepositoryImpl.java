/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.repositories.impl;

import com.nth_ntq.pojo.Notifications;
import com.nth_ntq.repositories.NotificationRepository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author trung
 */
@Repository
@Transactional
public class NotificationRepositoryImpl implements NotificationRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Notifications notification) {
        sessionFactory.getCurrentSession().saveOrUpdate(notification);
    }

    @Override
    public long countReminders(Long userId) {
        Session session = sessionFactory.getCurrentSession();
        Long cnt = session.createQuery(
            "SELECT COUNT(n) "
          + "FROM Notifications n "
          + "WHERE n.userId.userId = :uid "
          + "  AND n.type = 'REMINDER' "
          + "  AND n.isRead = false",
            Long.class)
          .setParameter("uid", userId)
          .uniqueResult();
        return cnt != null ? cnt : 0L;
    }

    @Override
    public List<Notifications> findByUser(Long userId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(
            "FROM Notifications n "
          + "WHERE n.userId.userId = :uid "
          + "ORDER BY n.createdAt DESC",
            Notifications.class)
          .setParameter("uid", userId)
          .list();
    }
}
