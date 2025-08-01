/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.controllers;

import com.nth_ntq.pojo.Courses;
import com.nth_ntq.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;

/**
 *
 * @author pc
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiCourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/courses")
    public ResponseEntity<List<Courses>> getCourses(@RequestParam Map<String, String> params) {
        List<Courses> courses = courseService.getCourses(params);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/courses/{courseId}")
    public ResponseEntity<?> getCourseDetail(@PathVariable(value = "courseId") Long courseId) {
        return new ResponseEntity<>(this.courseService.getCourseDetail(courseId), HttpStatus.OK);
    }
}
