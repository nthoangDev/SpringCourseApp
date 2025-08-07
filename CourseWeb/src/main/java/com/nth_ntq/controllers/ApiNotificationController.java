/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.controllers;

import com.nth_ntq.dto.NotificationDTO;
import com.nth_ntq.pojo.Enrollments;
import com.nth_ntq.services.EnrollmentService;
import com.nth_ntq.services.NotificationService;
import com.nth_ntq.services.UserService;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author trung
 */
@RestController
@RequestMapping("/api/secure/notifications")
public class ApiNotificationController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    /**
     * POST /api/secure/notifications/study-reminder/{courseId} Gửi ngay 1
     * notification nhắc học cho user hiện tại
     */
    @PostMapping("/study-reminder/{courseId}")
    public ResponseEntity<Void> sendStudyReminder(
            Principal principal,
            @PathVariable("courseId") Long courseId) {

        Long userId = userService
                .getUserByUsername(principal.getName())
                .getUserId();

        // 1) Kiểm tra enrollment & completed flag
        Enrollments en = enrollmentService
                .findByUserIdAndCourseId(userId, courseId) // ← gọi trên biến enrollmentService
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Chưa ghi danh khóa này"));

        if (en.isCompleted()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Bạn đã hoàn thành khóa này rồi");
        }

        // 2) Gửi notification
        notificationService.sendStudyReminder(userId, courseId);
        return ResponseEntity.ok().build();
    }

    /**
     * GET /api/secure/notifications/reminder/count
     */
    @GetMapping("/reminder/count")
    public ResponseEntity<Long> countReminders(Principal principal) {
        Long userId = userService
                .getUserByUsername(principal.getName())
                .getUserId();
        long cnt = notificationService.countUnreadReminders(userId);
        return ResponseEntity.ok(cnt);
    }

    /**
     * GET /api/secure/notifications
     */
    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getNotifications(
            Principal principal) {

        Long userId = userService
                .getUserByUsername(principal.getName())
                .getUserId();

        List<NotificationDTO> list = notificationService.getUserNotifications(userId);
        return ResponseEntity.ok(list);
    }
}
