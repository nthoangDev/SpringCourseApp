/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.controllers;

import com.nth_ntq.services.CourseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author pc
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiTeacherController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/secure/teacher/courses")
    public ResponseEntity<?> getCoursesForTeacher(HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        if (username == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Không có quyền truy cập!");

        return ResponseEntity.ok(courseService.getCoursesByTeacherUsername(username));
    }
}