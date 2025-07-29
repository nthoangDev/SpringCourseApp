/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.services.impl;

import com.nth_ntq.pojo.Assessments;
import com.nth_ntq.repositories.AssessmentRepository;
import com.nth_ntq.services.AssessmentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author pc
 */
@Service
public class AssessmentServiceImpl implements AssessmentService {

    @Autowired
    private AssessmentRepository assessmentRepo;

    @Override
    public List<Assessments> getAssessmentsByCourseId(Long courseId) {
        return this.assessmentRepo.getAssessmentsByCourseId(courseId);
    }

    @Override
    public Assessments getById(Long id) {
        return this.assessmentRepo.getById(id);
    }
}
