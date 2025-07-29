/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nth_ntq.repositories;

import com.nth_ntq.pojo.Lessons;
import java.util.List;

/**
 *
 * @author pc
 */
public interface LessonRepository {
    List<Lessons> getLessonsByCourseId(Long courseId);
    Lessons getLessonById(Long id);
    Lessons addOrUpdateLesson(Lessons l);
    void deleteLesson(Long id);
}
