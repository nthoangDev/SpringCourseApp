/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nth_ntq.repositories;

import com.nth_ntq.pojo.AssessmentResults;
import java.util.List;



/**
 *
 * @author pc
 */
public interface AssessmentResultRepository {
    List<AssessmentResults> getResultsByAssessment(Long assessmentId);
    AssessmentResults getResult(Long assessmentId, Long userId);
    AssessmentResults save(AssessmentResults r);
}