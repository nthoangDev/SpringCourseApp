/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.layout.Document;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.nth_ntq.pojo.*;
import com.nth_ntq.repositories.*;
import com.nth_ntq.services.CertificateService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author pc
 */
@Service
@Transactional
public class CertificateServiceImpl implements CertificateService {

    @Autowired
    private CertificateRepository certificateRepo;

    @Autowired
    private LessonRepository lessonRepo;

    @Autowired
    private AssessmentRepository assessmentRepo;

    @Autowired
    private AssessmentResultRepository assessmentResultRepo;

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public boolean checkCompletion(Long userId, Long courseId) {
        Long totalLessons = lessonRepo.countByCourseId(courseId);
        Long completedLessons = lessonRepo.countCompletedLessonsByUser(userId, courseId);

        if (!totalLessons.equals(completedLessons)) {
            return false;
        }

        List<Assessments> assessments = assessmentRepo.getAssessmentsByCourseId(courseId);
        for (Assessments a : assessments) {
            AssessmentResults r = assessmentResultRepo.findByUserAndAssessment(userId, a.getAssessmentId());
            if (r == null || r.getScore().compareTo(BigDecimal.valueOf(5.0)) < 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Certificates getCertificateByUserAndCourse(Long userId, Long courseId) {
        Certificates cert = certificateRepo.findByUserAndCourse(userId, courseId);
        if (cert != null) {
            return cert;
        }

        if (!checkCompletion(userId, courseId)) {
            throw new IllegalStateException("User chưa đủ điều kiện cấp chứng chỉ");
        }

        Courses course = courseRepo.getCourseById(courseId);
        Users user = userRepo.getUserById(userId);

        // Sinh PDF chứng chỉ
        String fileUrl = generatePdfAndUpload(user, course);

        cert = new Certificates();
        cert.setUserId(user);
        cert.setCourseId(course);
        cert.setIssuedAt(new Date());
        cert.setCertificateUrl(fileUrl);

        certificateRepo.save(cert);
        return cert;
    }

    public String generatePdfAndUpload(Users user, Courses course) {
        try {
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            document.open();

            InputStream fontStream = getClass().getClassLoader().getResourceAsStream("fonts/Roboto-Regular.ttf");

            if (fontStream == null) {
                throw new RuntimeException("Không tìm thấy font Roboto!");
            }

            BaseFont baseFont = BaseFont.createFont("Roboto-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true, fontStream.readAllBytes(), null);
            Font titleFont = new Font(baseFont, 24, Font.BOLD, BaseColor.BLUE);
            Font contentFont = new Font(baseFont, 14, Font.NORMAL, BaseColor.BLACK);

            document.add(new Paragraph("CHỨNG CHỈ HOÀN THÀNH KHÓA HỌC", titleFont));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Người học: " + user.getFullName(), contentFont));
            document.add(new Paragraph("Email: " + user.getEmail(), contentFont));
            document.add(new Paragraph("Tên khóa học: " + course.getTitle(), contentFont));
            document.add(new Paragraph("Ngày cấp: " + new Date(), contentFont));
            document.add(new Paragraph("\nChúc mừng bạn đã hoàn thành khóa học!", contentFont));

            document.close();

            // Upload lên Cloudinary
            Map uploadResult = cloudinary.uploader().upload(baos.toByteArray(), ObjectUtils.asMap(
                    "resource_type", "auto",
                    "type", "upload", 
                    "folder", "certificates/",
                    "public_id", "cert_user_" + user.getUserId() + "_course_" + course.getCourseId(),
                    "format", "pdf"
            )
            );

            return uploadResult.get("secure_url").toString();

        } catch (Exception e) {
            throw new RuntimeException("Lỗi sinh chứng chỉ PDF: " + e.getMessage());
        }
    }

}
