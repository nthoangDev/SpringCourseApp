/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.services.impl;

import com.nth_ntq.dto.NotificationDTO;
import com.nth_ntq.pojo.Notifications;

import com.nth_ntq.pojo.Users;
import com.nth_ntq.pojo.Courses;
import com.nth_ntq.repositories.NotificationRepository;
import com.nth_ntq.repositories.UserRepository;
import com.nth_ntq.repositories.CourseRepository;
import com.nth_ntq.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author trung
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired private NotificationRepository notifRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private CourseRepository courseRepo;

    @Override
    @Transactional
    public void sendStudyReminder(Long userId, Long courseId) {
        Users user = userRepo.findById(userId);
        Courses course = courseRepo.getCourseById(courseId);

        Notifications n = new Notifications();
        n.setUserId(user);
        n.setCourseId(course);
        n.setType("REMINDER");
        n.setIsRead(false);
        n.setMessage("Nhắc bạn tiếp tục học khóa “" + course.getTitle() + "” nhé!");
        n.setCreatedAt(new java.util.Date());

        notifRepo.save(n);
    }

    @Override
    @Transactional(readOnly = true)
    public long countUnreadReminders(Long userId) {
        return notifRepo.countReminders(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDTO> getUserNotifications(Long userId) {
        return notifRepo.findByUser(userId).stream()
            .map(n -> new NotificationDTO(
                n.getNotificationId(),
                n.getCourseId() != null ? n.getCourseId().getCourseId() : null,
                n.getMessage(),
                n.getType(),
                n.getIsRead() != null ? n.getIsRead() : false,
                n.getCreatedAt()
            ))
            .collect(Collectors.toList());
    }
}   