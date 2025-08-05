/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.services.impl;

import com.nth_ntq.dto.AssessmentDTO;
import com.nth_ntq.pojo.Assessments;
import com.nth_ntq.pojo.Assignments;
import com.nth_ntq.pojo.Tests;
import com.nth_ntq.pojo.Courses;
import com.nth_ntq.repositories.AssessmentRepository;
import com.nth_ntq.repositories.CourseRepository;
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

    @Autowired
    private CourseRepository courseRepo;

    @Override
    public List<Assessments> getAssessmentsByCourseId(Long courseId) {
        return this.assessmentRepo.getAssessmentsByCourseId(courseId);
    }

    @Override
    public Assessments getById(Long id) {
        return this.assessmentRepo.getById(id);
    }

    @Override
    public Assessments saveAssessment(AssessmentDTO dto) {
        // Lấy khóa học liên quan
        Courses c = courseRepo.getCourseById(dto.getCourseId());

        // Tạo hoặc lấy lại đối tượng Assessments
        Assessments a = new Assessments();

        if (dto.getAssessmentId() != null) {
            // Nếu đang sửa, lấy từ DB để cập nhật
            a = assessmentRepo.getById(dto.getAssessmentId());
            if (a == null) {
                throw new RuntimeException("Assessment không tồn tại!");
            }
        }

        // Set các thuộc tính chung
        a.setAssessmentType(dto.getAssessmentType());
        a.setAvailableAt(dto.getAvailableAt());
        a.setDueAt(dto.getDueAt());
        a.setTitle(dto.getTitle());
        a.setDescription(dto.getDescription());
        a.setPoints(dto.getPoints());
        a.setCourseId(c);

        // Lưu assessments trước để có ID (nếu mới)
        a = assessmentRepo.addOrUpdateAssessment(a); // <== đây là nơi persist hoặc merge

        // Gọi tiếp phần riêng ASSIGNMENT hoặc TEST
        if ("ASSIGNMENT".equalsIgnoreCase(dto.getAssessmentType())) {
            Assignments as = new Assignments();
            as.setAssignmentId(a.getAssessmentId()); // khóa chính 1-1
            as.setDueDate(dto.getDueDate());
            a.setAssignments(as);
            assessmentRepo.addAssignment(as);
        } else if ("TEST".equalsIgnoreCase(dto.getAssessmentType())) {
            Tests t = new Tests();
            t.setTestId(a.getAssessmentId());
            t.setDurationMinutes(dto.getDurationMinutes());
            a.setTests(t);
            assessmentRepo.addTest(t);
        }

        return a;
    }

    @Override
    public void delete(Long id) {
        assessmentRepo.deleteAssessment(id);
    }

    @Override
    public List<Assessments> getAssessmentsByLessonId(Long lessonId) {
        return assessmentRepo.getAssessmentsByLessonId(lessonId);
    }
}
