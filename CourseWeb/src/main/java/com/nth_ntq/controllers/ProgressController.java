/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.controllers;

import com.nth_ntq.dto.ProgressDTO;
import com.nth_ntq.services.ProgressService;
import com.nth_ntq.services.UserService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author trung
 */
@RestController
@RequestMapping("/api")
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    @Autowired
    private UserService userService;

    /**
     * GET /api/secure/courses/{courseId}/progress Trả về tiến độ học của user
     * hiện tại cho courseId.
     */
    @GetMapping("/secure/courses/{courseId}/progress")
    public ResponseEntity<?> getCourseProgress(Principal principal,
            @PathVariable Long courseId) {
        if (principal == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Chưa đăng nhập");
        }
        // Lấy userId từ principal
        Long userId = userService
                .getUserByUsername(principal.getName())
                .getUserId();

        ProgressDTO dto = progressService.getProgress(userId, courseId);
        return ResponseEntity.ok(dto);
    }
}
