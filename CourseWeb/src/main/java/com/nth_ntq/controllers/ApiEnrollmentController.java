/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.controllers;

import com.nth_ntq.pojo.Users;
import com.nth_ntq.pojo.Enrollments;
import com.nth_ntq.services.EnrollmentService;
import com.nth_ntq.services.UserService;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;              // <— đúng import SLF4J
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author trung
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiEnrollmentController {

    private final EnrollmentService enrollmentService;
    private final UserService userService;
    private static final Logger LOGGER =
        LoggerFactory.getLogger(ApiEnrollmentController.class);
    public ApiEnrollmentController(EnrollmentService enrollmentService,
            UserService userService) {
        this.enrollmentService = enrollmentService;
        this.userService = userService;
    }

    @GetMapping("/my-courses")
    public ResponseEntity<?> myCourses(Principal principal) {
        LOGGER.info("==> /api/my-courses called, principal = {}", principal);

        if (principal == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Chưa đăng nhập");
        }

        try {
            // Lấy user hiện tại
            Users u = userService.getUserByUsername(principal.getName());
            // Lấy danh sách enrollments
            List<Enrollments> ens = enrollmentService.getByUserId(u.getUserId());
            // Chuyển thành DTO
            List<CourseDTO> list = ens.stream()
                    .map(e -> new CourseDTO(
                    e.getCourseId().getCourseId(),
                    e.getCourseId().getTitle(),
                    e.getCourseId().getImageUrl(),
                    e.getCourseId().getDescription()
            ))
                    .collect(Collectors.toList());
            // Trả về JSON danh sách CourseDTO
            return ResponseEntity.ok(list);

        } catch (Exception ex) {
            LOGGER.error("Error in myCourses:", ex);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Server error: " + ex.getMessage());
        }
    }

    // DTO tĩnh nội bộ
    public static class CourseDTO {

        public Long courseId;
        public String title;
        public String imageUrl;
        public String description;

        public CourseDTO(Long id, String t, String img, String desc) {
            this.courseId = id;
            this.title = t;
            this.imageUrl = img;
            this.description = desc;
        }
    }
}
