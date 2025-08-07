/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nth_ntq.services;

import com.nth_ntq.pojo.AssessmentResults;
import jakarta.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author pc
 */
public interface AssessmentResultService {
    List<AssessmentResults> getResultsByAssessment(Long assessmentId, HttpServletRequest request);
    AssessmentResults getResult(Long assessmentId, Long userId, HttpServletRequest request);
    void grade(Long assessmentId, Long userId, BigDecimal score, String feedback, HttpServletRequest request);
}