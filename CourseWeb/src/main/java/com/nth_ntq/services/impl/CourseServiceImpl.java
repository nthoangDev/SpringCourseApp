/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nth_ntq.pojo.Courses;
import com.nth_ntq.pojo.Tags;
import com.nth_ntq.repositories.CommentRepository;
import com.nth_ntq.repositories.CourseRepository;
import com.nth_ntq.repositories.EnrollmentRepository;
import com.nth_ntq.repositories.LessonRepository;
import com.nth_ntq.repositories.LikeRepository;
import com.nth_ntq.repositories.RatingRepository;
import com.nth_ntq.services.CourseService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author pc
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private LikeRepository likeRepo;

    @Autowired
    private RatingRepository ratingRepo;

    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private LessonRepository lessonRepo;

    @Autowired
    private EnrollmentRepository enrollmentRepo;

    @Override
    public List<Courses> getCourses(Map<String, String> params) {
        return this.courseRepo.getCourses(params);
    }

    @Override
    public Courses getCourseById(Long id) {
        return this.courseRepo.getCourseById(id);
    }

    @Override
    public Courses addOrUpdateCourse(Courses c) {
        if (c.getCourseId() != null && (c.getFile() == null || c.getFile().isEmpty())) {
            Courses old = this.courseRepo.getCourseById(c.getCourseId());
            c.setImageUrl(old.getImageUrl());
        }

        if (c.getFile() != null && !c.getFile().isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(c.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                c.setImageUrl(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(CourseServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return this.courseRepo.addOrUpdateCourse(c);
    }

    @Override
    public void deleteCourse(Long id) {
        this.courseRepo.deleteCourse(id);
    }

    @Override
    public long countCourse() {
        return this.courseRepo.countCourse();
    }

    @Override
    public Map<String, Object> getCourseDetail(Long courseId) {
        Map<String, Object> result = new HashMap<>();

        Courses c = this.courseRepo.getCourseById(courseId);
        if (c == null) {
            return result;
        }

        // 1. Thông tin cơ bản
        result.put("course", c);
        result.put("tags", c.getTagsSet().stream().map(Tags::getName).toList());

        // 2. Số lượng bài học và lượt đăng ký
        result.put("lessonCount", lessonRepo.countByCourse(courseId));
        result.put("enrollmentCount", enrollmentRepo.countByCourse(courseId));

        // 3. Phản hồi người dùng
        Map<String, Object> feedback = new HashMap<>();
        feedback.put("likes", likeRepo.countLike(courseId));
        feedback.put("ratings", ratingRepo.getRatingSummary(courseId));
        feedback.put("comments", commentRepo.getCommentsByCourse(courseId).stream()
                .map(cmt -> Map.of(
                "commentId", cmt.getCommentId(),
                "userFullName", cmt.getUserId().getFullName(),
                "content", cmt.getContent(),
                "createdAt", cmt.getCreatedAt()
        )).toList());

        result.put("feedback", feedback);

        return result;
    }

}
