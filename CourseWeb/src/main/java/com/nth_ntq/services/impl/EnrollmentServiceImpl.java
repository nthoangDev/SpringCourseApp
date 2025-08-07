/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.services.impl;

import com.nth_ntq.pojo.Enrollments;
import com.nth_ntq.repositories.EnrollmentRepository;
import com.nth_ntq.services.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 *
 * @author pc
 */
@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepo;

    @Override
    public void save(Enrollments enrollment) {
        enrollmentRepo.save(enrollment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enrollments> getByUserId(Long userId) {
        return enrollmentRepo.findByUserId(userId);

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Enrollments> findByUserIdAndCourseId(Long userId, Long courseId) {
        return enrollmentRepo.findByUserIdAndCourseId(userId, courseId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enrollments> findPendingEnrollments() {
        return enrollmentRepo.findByCompletedFalse();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enrollments> findPendingByUsername(String username) {
        return enrollmentRepo.findPendingByUsername(username);
    }

}
