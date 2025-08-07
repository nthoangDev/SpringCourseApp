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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        // Tổng số bài học trong khóa
        long total = lessonRepo.countByCourseId(courseId);
        // Số bài học học viên đã hoàn thành
        long done = lessonRepo.countCompletedLessonsByUser(userId, courseId);

        // Tính tỉ lệ hoàn thành (nếu không có bài thì xem là 100%)
        double pct = (total == 0)
                ? 100.0
                : (done * 100.0 / total);

        // Xem đã hoàn thành khóa (tất cả bài học) hay chưa
        boolean completed = (done == total);

        // Trả về DTO (constructor: done, total, pct, completed)
        return new ProgressDTO(done, total, pct, completed);
    }

}
