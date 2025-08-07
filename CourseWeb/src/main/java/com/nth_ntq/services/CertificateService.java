/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nth_ntq.services;

import com.nth_ntq.pojo.Certificates;
import com.nth_ntq.pojo.Courses;
import com.nth_ntq.pojo.Users;

/**
 *
 * @author pc
 */
public interface CertificateService {

    boolean checkCompletion(Long userId, Long courseId);
//    Certificates generateCertificate(Long userId, Long courseId);

    String generatePdfAndUpload(Users user, Courses course);

    Certificates getCertificateByUserAndCourse(Long userId, Long courseId);
}
