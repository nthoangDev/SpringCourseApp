/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nth_ntq.services;

import com.nth_ntq.dto.AssessmentDTO;
import com.nth_ntq.pojo.Assessments;
import java.util.List;

/**
 *
 * @author pc
 */
public interface AssessmentService {
    Assessments saveAssessment(AssessmentDTO dto);
    List<Assessments> getAssessmentsByCourseId(Long courseId);
    Assessments getById(Long id);
    void delete(Long id);
}
