/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.services;

import com.nth_ntq.pojo.Users;
import java.util.List;

/**
 *
 * @author trung
 */
public interface TeacherService {
    List<Users> getTeachers();
    Users getTeacherById(Long id);
    void addOrUpdateTeacher(Users u);
    void deleteTeacher(Long id);
    
    List<Users> getUsersByRole(String role);
    void promoteToTeacher(Long userId);
    void demoteToUser(Long userId);
    
    List<Users> getTeachersByKeyword(String kw);
}
