/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.controllers;

import com.nth_ntq.pojo.Assessments;
import com.nth_ntq.services.AssessmentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author pc
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiAssessmentController {

    @Autowired
    private AssessmentService assessmentService;

    @GetMapping("/secure/lessons/{lessonId}/assessments")
    public ResponseEntity<?> getAssessmentsByLesson(@PathVariable(value = "lessonId") Long lessonId) {
        List<Assessments> assessments = assessmentService.getAssessmentsByLessonId(lessonId);
        return ResponseEntity.ok(assessments);
    }
}

