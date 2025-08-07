/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.controllers;

import com.nth_ntq.pojo.Certificates;
import com.nth_ntq.pojo.Users;
import com.nth_ntq.repositories.CertificateRepository;
import com.nth_ntq.services.CertificateService;
import com.nth_ntq.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author pc
 */
@RestController
@RequestMapping("/api")
public class ApiCertificateController {

    @Autowired
    private CertificateService certificateService;

    @Autowired
    private UserService userService;

    @Autowired
    private CertificateRepository certificateRepo;

    @PostMapping("/secure/certificates/generate/{courseId}")
    public ResponseEntity<?> generateCertificate(@PathVariable(value = "courseId") Long courseId, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        Users u = userService.getUserByUsername(username);
        Certificates cert = certificateService.getCertificateByUserAndCourse(u.getUserId(), courseId);
        return ResponseEntity.ok(cert);
    }

}
