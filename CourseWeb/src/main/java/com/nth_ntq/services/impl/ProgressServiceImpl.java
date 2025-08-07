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
    @Autowired private LessonRepository lessonRepo;
    @Autowired private EnrollmentRepository enrollRepo;

    @Override
    public ProgressDTO getProgress(Long userId, Long courseId) {
        long total = lessonRepo.countByCourseId(courseId);
        Enrollments en = enrollRepo
            .findByUserIdAndCourseId(userId, courseId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Chưa ghi danh vào khóa này"));
        double pct = en.getProgress().doubleValue();
        long done = Math.round(total * pct / 100.0);
        return new ProgressDTO(done, total, pct, en.isCompleted());
    }
}

