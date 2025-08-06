/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nth_ntq.repositories;

import com.nth_ntq.pojo.Certificates;


/**
 *
 * @author pc
 */
public interface CertificateRepository {
    Certificates findByUserAndCourse(Long userId, Long courseId);
    void save(Certificates cert);
}
