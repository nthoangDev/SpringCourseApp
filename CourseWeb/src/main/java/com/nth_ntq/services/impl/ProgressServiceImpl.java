/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.services.impl;

import com.nth_ntq.dto.ProgressDTO;
import com.nth_ntq.pojo.Enrollments;
import com.nth_ntq.repositories.EnrollmentRepository;
import com.nth_ntq.repositories.LessonRepository;
import com.nth_ntq.services.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author trung
 */
@Service
public class ProgressServiceImpl implements ProgressService {

    @Autowired
    private LessonRepository lessonRepo;

    @Autowired
    private EnrollmentRepository enrollRepo;

    @Override
    public ProgressDTO getProgress(Long userId, Long courseId) {
        // 1) Tổng số bài
        long total = lessonRepo.countByCourseId(courseId);

        // 2) Lấy enrollment
        Enrollments en = enrollRepo
                .findByUserIdAndCourseId(userId, courseId)
                .orElseThrow(()
                        -> new RuntimeException("User chưa ghi danh vào khóa này"));

        double pct = en.getProgress().doubleValue();          // ví dụ 75.00
        boolean doneAll = en.isCompleted();      // true/false

        // 3) Tính số bài đã học (làm tròn)
        long doneLessons = Math.round(total * pct / 100.0);

        return new ProgressDTO(doneLessons, total, pct, doneAll);
    }
}
