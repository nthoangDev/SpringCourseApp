/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.repositories;

import com.nth_ntq.pojo.Users;
import java.util.List;

/**
 *
 * @author trung
 */
public interface UserRepository {
   List<Users> getUsersByKeyword(String kw);
   Users getUserByUsername(String username);
   Users addUser(Users u);
   boolean authenticate(String username, String password);
   Users getUserById(Long userId);
   Users findById(Long id);
   List<Users> findByRole(String role);
   List<Users> getStudentsByCourseId(Long courseId);
}
