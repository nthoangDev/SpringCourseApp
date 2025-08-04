/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.services.impl;

import com.nth_ntq.pojo.CourseTeachers;
import com.nth_ntq.pojo.Courses;
import com.nth_ntq.pojo.Users;
import com.nth_ntq.repositories.CourseTeachersRepository;
import com.nth_ntq.repositories.CourseRepository;
import com.nth_ntq.repositories.UserRepository;
import com.nth_ntq.services.CourseTeachersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author trung
 */
@Service
public class CourseTeachersServiceImpl implements CourseTeachersService {

    private final CourseTeachersRepository ctRepo;
    private final CourseRepository courseRepo;
    private final UserRepository userRepo;

    public CourseTeachersServiceImpl(CourseTeachersRepository ctRepo,
            CourseRepository courseRepo,
            UserRepository userRepo) {
        this.ctRepo = ctRepo;
        this.courseRepo = courseRepo;
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public CourseTeachers assign(Long courseId,
            Long teacherUserId,
            String roleDesc) {
        // 1. Lấy course
        Courses course = courseRepo.getCourseById(courseId);

        // 2. Lấy user (giảng viên) theo ID
        Users teacher = userRepo.findById(teacherUserId);
        if (teacher == null) {
            throw new NoSuchElementException(
                    "Giảng viên không tồn tại: " + teacherUserId);
        }

        // 3. Tạo entity liên kết
        CourseTeachers ct = new CourseTeachers();
        ct.setCourseId(course);
        ct.setUserId(teacher);
        ct.setRoleDesc(roleDesc);

        // 4. Lưu
        return ctRepo.add(ct);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseTeachers> getByCourse(Long courseId) {
        return ctRepo.findByCourseId(courseId);
    }
}
