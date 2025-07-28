/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.services.impl;

import com.nth_ntq.pojo.Users;
import com.nth_ntq.repositories.TeacherRepository;
import com.nth_ntq.services.TeacherService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author trung
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepo;

    @Override
    public List<Users> getTeachers() {
        return teacherRepo.getTeachers();
    }

    @Override
    public Users getTeacherById(Long id) {
        return teacherRepo.getTeacherById(id);
    }

    @Override
    public void addOrUpdateTeacher(Users u) {
        u.setRole("TEACHER");
        teacherRepo.addOrUpdateTeacher(u);
    }

    @Override
    public void deleteTeacher(Long id) {
        teacherRepo.deleteTeacher(id);
    }

    @Override
    public List<Users> getUsersByRole(String role) {
        return teacherRepo.getUsersByRole(role);
    }

    @Override
    public void promoteToTeacher(Long userId) {
        Users u = teacherRepo.getTeacherById(userId);
        u.setRole("TEACHER");
        teacherRepo.addOrUpdateTeacher(u);
    }

    @Override
    public void demoteToUser(Long userId) {
        Users u = teacherRepo.getTeacherById(userId);
        u.setRole("USER");
        teacherRepo.addOrUpdateTeacher(u);
    }

    @Override
    public List<Users> getTeachersByKeyword(String kw) {
        return teacherRepo.getTeachersByKeyword(kw);
    }
}
