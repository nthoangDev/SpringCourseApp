/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
<<<<<<< HEAD
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
=======
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
>>>>>>> 824a13972f6d9c5a5bc97e542c45c059e6b5211c
 */
package com.nth_ntq.services;

import com.nth_ntq.pojo.Enrollments;
import java.util.List;


/**
 *
 * @author pc
 */
public interface EnrollmentService {

    void save(Enrollments enrollment);

    List<Enrollments> getByUserId(Long userId);
}
