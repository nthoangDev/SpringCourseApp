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
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author trung
 */
@RestController
@RequestMapping("/api/secure/courses")
public class ApiProgressController {

    @Autowired
    private ProgressService progressService;
    @Autowired
    private UserService userService;

    /**
     * GET /api/secure/courses/{courseId}/progress
     */
    @GetMapping("/{courseId}/progress")
    public ResponseEntity<ProgressDTO> getCourseProgress(
            Principal principal,
            @PathVariable("courseId") Long courseId) {

        Long userId = userService
                .getUserByUsername(principal.getName())
                .getUserId();

        ProgressDTO dto = progressService.getProgress(userId, courseId);
        return ResponseEntity.ok(dto);
    }

}
