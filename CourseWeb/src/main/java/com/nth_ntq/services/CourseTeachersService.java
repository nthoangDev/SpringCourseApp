/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.services;

import com.nth_ntq.pojo.CourseTeachers;
import java.util.List;

/**
 *
 * @author trung
 */
public interface CourseTeachersService {
    CourseTeachers assign(Long courseId, Long teacherUserId, String roleDesc);
    List<CourseTeachers> getByCourse(Long courseId);
}