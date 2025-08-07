/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.services;

import java.util.List;
import com.nth_ntq.dto.NotificationDTO;

/**
 *
 * @author trung
 */
public interface NotificationService {
    void sendStudyReminder(Long userId, Long courseId);

    long countUnreadReminders(Long userId);

    List<NotificationDTO> getUserNotifications(Long userId);
}
