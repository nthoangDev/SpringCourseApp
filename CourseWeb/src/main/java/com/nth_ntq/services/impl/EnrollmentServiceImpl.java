/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.services.impl;

import com.nth_ntq.pojo.Enrollments;
import com.nth_ntq.repositories.EnrollmentRepository;
import com.nth_ntq.services.EnrollmentService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 *
 * @author trung
 */
@Service
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepository enrollRepo;

    public EnrollmentServiceImpl(EnrollmentRepository enrollRepo) {
        this.enrollRepo = enrollRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Enrollments> getByUserId(Long userId) {
        return enrollRepo.findByUserId(userId);
    }
}
