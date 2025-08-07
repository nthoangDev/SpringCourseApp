/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nth_ntq.repositories;

import com.nth_ntq.pojo.Enrollments;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author pc
 */
public interface EnrollmentRepository {

    Enrollments save(Enrollments e);

    long countByCourse(Long courseId);

    boolean existsByUserAndCourse(Long userId, Long courseId);

    List<Enrollments> findByUserId(Long userId);

    Optional<Enrollments> findByUserIdAndCourseId(Long userId, Long courseId);

    List<Enrollments> findByCompletedFalse();

   List<Enrollments> findPendingByUsername(String username);
}
