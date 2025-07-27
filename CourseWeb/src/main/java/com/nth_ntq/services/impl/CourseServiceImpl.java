/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.services.impl;

import com.nth_ntq.pojo.Courses;
import com.nth_ntq.repositories.CourseRepository;
import com.nth_ntq.services.CourseService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author pc
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepo;

    @Override
    public List<Courses> getCourses(Map<String, String> params) {
        return this.courseRepo.getCourses(params);
    }

    @Override
    public Courses getCourseById(Long id) {
        return this.courseRepo.getCourseById(id);
    }

    @Override
    public void addOrUpdateCourse(Courses c) {
        this.courseRepo.addOrUpdateCourse(c);
    }

    @Override
    public void deleteCourse(Long id) {
        this.courseRepo.deleteCourse(id);
    }

    @Override
    public long countCourse() {
        return this.courseRepo.countCourse();
    }
}