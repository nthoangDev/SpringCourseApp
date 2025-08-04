/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.repositories;

import com.nth_ntq.pojo.CourseTeachers;
import java.util.List;

/**
 *
 * @author trung
 */
public interface CourseTeachersRepository {
    CourseTeachers add(CourseTeachers ct);
    List<CourseTeachers> findByCourseId(Long courseId);
}