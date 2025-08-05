/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.controllers;

import com.nth_ntq.services.AssessmentResultService;
import jakarta.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author pc
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiAssessmentResultController {

    @Autowired
    private AssessmentResultService resultService;

    @PostMapping("/secure/assessments/{assessmentId}/grade/{userId}")
    public ResponseEntity<?> grade(
            @PathVariable(value = "assessmentId") Long assessmentId,
            @PathVariable(value = "userId") Long userId,
            @RequestParam("score") BigDecimal score,
            @RequestParam(value = "feedback", required = false) String feedback,
            HttpServletRequest request
    ) {
        resultService.grade(assessmentId, userId, score, feedback, request);
        return ResponseEntity.ok("Đã chấm điểm!");
    }

    @GetMapping("/secure/assessments/{assessmentId}/results")
    public ResponseEntity<?> viewAll(
            @PathVariable(value = "assessmentId") Long assessmentId,
            HttpServletRequest request) {
        return ResponseEntity.ok(resultService.getResultsByAssessment(assessmentId, request));
    }

    @GetMapping("/secure/assessments/{assessmentId}/results/{userId}")
    public ResponseEntity<?> getResult(
            @PathVariable(value = "assessmentId") Long assessmentId,
            @PathVariable(value = "userId") Long userId,
            HttpServletRequest request) {
        return ResponseEntity.ok(resultService.getResult(assessmentId, userId, request));
    }
}
