/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nth_ntq.pojo.Courses;
import com.nth_ntq.repositories.CourseRepository;
import com.nth_ntq.services.CourseService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public List<Courses> getCourses(Map<String, String> params) {
        return this.courseRepo.getCourses(params);
    }

    @Override
    public Courses getCourseById(Long id) {
        return this.courseRepo.getCourseById(id);
    }

    @Override
    public Courses addOrUpdateCourse(Courses c) {
        if (c.getCourseId() != null && (c.getFile() == null || c.getFile().isEmpty())) {
            Courses old = this.courseRepo.getCourseById(c.getCourseId());
            c.setImageUrl(old.getImageUrl());
        }
        
        if (c.getFile() != null && !c.getFile().isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(c.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                c.setImageUrl(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(CourseServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return this.courseRepo.addOrUpdateCourse(c);
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
