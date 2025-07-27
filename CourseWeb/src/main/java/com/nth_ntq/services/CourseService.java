/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nth_ntq.services;

import com.nth_ntq.pojo.Courses;
import java.util.List;
import java.util.Map;

/**
 *
 * @author pc
 */
public interface CourseService {
    List<Courses> getCourses(Map<String, String> params);
    Courses getCourseById(Long id);
    void addOrUpdateCourse(Courses c);
    void deleteCourse(Long id);
    long countCourse();
}