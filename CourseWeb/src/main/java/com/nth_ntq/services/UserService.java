/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.services;

import com.nth_ntq.pojo.Users;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author trung
 */
public interface UserService extends UserDetailsService {

    List<Users> getUsersByKeyword(String kw);

    Users getUserByUsername(String username);
    
    Users addUser(Map<String, String> params, MultipartFile avatar);

    boolean authenticate(String username, String password);
    
    Users getUserById(Long id);
    List<Users> getAllTeachers();
    
    List<Users> getStudentsByCourseId(Long courseId);
}
