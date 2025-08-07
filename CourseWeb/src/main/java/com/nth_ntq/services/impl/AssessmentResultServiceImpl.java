/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.services.impl;

import com.nth_ntq.pojo.AssessmentResults;
import com.nth_ntq.pojo.AssessmentResultsPK;
import com.nth_ntq.pojo.Assessments;
import com.nth_ntq.pojo.Courses;
import com.nth_ntq.pojo.Users;
import com.nth_ntq.repositories.AssessmentRepository;
import com.nth_ntq.repositories.AssessmentResultRepository;
import com.nth_ntq.repositories.UserRepository;
import com.nth_ntq.services.AssessmentResultService;
import jakarta.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author pc
 */
@Service
@Transactional
public class AssessmentResultServiceImpl implements AssessmentResultService {

    @Autowired
    private AssessmentResultRepository resultRepo;

    @Autowired
    private AssessmentRepository assessmentRepo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public void grade(Long aid, Long uid, BigDecimal score, String feedback, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        Users currentUser = userRepo.getUserByUsername(username);

        Assessments assessment = assessmentRepo.getById(aid);
        Courses course = assessment.getCourseId();

        boolean isTeacher = course.getCourseTeachersSet()
                .stream()
                .anyMatch(ct -> ct.getUserId().getUserId().equals(currentUser.getUserId()));

        if (!isTeacher) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Bạn không có quyền chấm bài đánh giá này!"
            );
        }
        Users u = userRepo.getUserById(uid);
        AssessmentResultsPK pk = new AssessmentResultsPK(aid, uid);
        AssessmentResults r = new AssessmentResults(pk);

        r.setAssessments(assessment);
        r.setUsers(u);
        r.setScore(score);
        r.setFeedback(feedback);
        r.setCompletedAt(new Date());

        resultRepo.save(r);
    }

    @Override
    public List<AssessmentResults> getResultsByAssessment(Long assessmentId, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        Users currentUser = userRepo.getUserByUsername(username);

        Assessments assessment = assessmentRepo.getById(assessmentId);
        Courses course = assessment.getCourseId();

        boolean isTeacher = course.getCourseTeachersSet()
                .stream()
                .anyMatch(ct -> ct.getUserId().getUserId().equals(currentUser.getUserId()));

        if (!isTeacher) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Bạn không có quyền xem kết quả bài đánh giá này!"
            );
        }

        return resultRepo.getResultsByAssessment(assessmentId);
    }

    @Override
    public AssessmentResults getResult(Long assessmentId, Long userId, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        Users currentUser = userRepo.getUserByUsername(username);

        Assessments assessment = assessmentRepo.getById(assessmentId);
        Courses course = assessment.getCourseId();

        boolean isTeacher = course.getCourseTeachersSet()
                .stream()
                .anyMatch(ct -> ct.getUserId().getUserId().equals(currentUser.getUserId()));

        if (!isTeacher && !currentUser.getUserId().equals(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Bạn không có quyền xem kết quả bài đánh giá này!"
            );
        }

        return resultRepo.getResult(assessmentId, userId);
    }
}
