/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.services.impl;

import com.nth_ntq.pojo.Lessons;
import com.nth_ntq.repositories.LessonRepository;
import com.nth_ntq.services.LessonService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author pc
 */
@Service
public class LessonServiceImpl implements LessonService {
    @Autowired
    private LessonRepository lessonRepo;

    @Override
    public List<Lessons> getLessonsByCourseId(Long courseId) {
        return this.lessonRepo.getLessonsByCourseId(courseId);
    }

    @Override
    public Lessons getLessonById(Long id) {
        return this.lessonRepo.getLessonById(id);
    }

    @Override
    public Lessons addOrUpdateLesson(Lessons l) {
        return this.lessonRepo.addOrUpdateLesson(l);
    }

    @Override
    public void deleteLesson(Long id) {
        this.lessonRepo.deleteLesson(id);
    }
}