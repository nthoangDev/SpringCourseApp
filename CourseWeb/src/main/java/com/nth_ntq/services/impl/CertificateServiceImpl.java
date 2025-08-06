/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.services.impl;

import com.nth_ntq.pojo.AssessmentResults;
import com.nth_ntq.pojo.Assessments;
import com.nth_ntq.pojo.Certificates;
import com.nth_ntq.repositories.AssessmentRepository;
import com.nth_ntq.repositories.AssessmentResultRepository;
import com.nth_ntq.repositories.CertificateRepository;
import com.nth_ntq.repositories.CourseRepository;
import com.nth_ntq.repositories.LessonRepository;
import com.nth_ntq.repositories.UserRepository;
import com.nth_ntq.services.CertificateService;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author pc
 */
@Transactional
@Service
public class CertificateServiceImpl implements CertificateService {

    @Autowired
    private CertificateRepository certificateRepo;

    @Autowired
    private LessonRepository lessonRepo;

    @Autowired
    private AssessmentRepository assessmentRepo;

    @Autowired
    private AssessmentResultRepository assessmentResultRepo;

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public boolean checkCompletion(Long userId, Long courseId) {
        Long totalLessons = lessonRepo.countByCourseId(courseId);
        Long completedLessons = lessonRepo.countCompletedLessonsByUser(userId, courseId);

        if (!totalLessons.equals(completedLessons)) {
            return false;
        }

        List<Assessments> assessments = assessmentRepo.getAssessmentsByCourseId(courseId);
        for (Assessments a : assessments) {
            AssessmentResults r = assessmentResultRepo.findByUserAndAssessment(userId, a.getAssessmentId());
//            System.out.printf("Assessment %d: %s -> Score = %s%n",
//                    a.getAssessmentId(), a.getTitle(), r != null ? r.getScore() : "null");
            if (r == null || r.getScore().compareTo(BigDecimal.valueOf(5.0)) < 0) {
                return false;
            }

        }

        return true;
    }

    @Override
    public Certificates generateCertificate(Long userId, Long courseId) {
        if (certificateRepo.findByUserAndCourse(userId, courseId) != null) {
            return certificateRepo.findByUserAndCourse(userId, courseId); // đã cấp rồi
        }
        if (!checkCompletion(userId, courseId)) {
            throw new IllegalStateException("User chưa đủ điều kiện cấp chứng chỉ");
        }

        Certificates c = new Certificates();
        c.setUserId(userRepo.getUserById(userId));
        c.setCourseId(courseRepo.getCourseById(courseId));
        c.setIssuedAt(new Date());

        // Nếu muốn thêm link file:
        // String url = certificateGenerator.generate(userId, courseId); // Optional
        // c.setCertificateUrl(url);
        certificateRepo.save(c);
        return c;
    }

    @Transactional
    @Override
    public Certificates getCertificateByUserAndCourse(Long userId, Long courseId) {
        return certificateRepo.findByUserAndCourse(userId, courseId);
    }
}
