/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.controllers;

import com.nth_ntq.pojo.Lessons;
import com.nth_ntq.services.AssessmentService;
import com.nth_ntq.services.CourseService;
import com.nth_ntq.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author pc
 */
@Controller
@RequestMapping("/admin/courses/{courseId}/lessons")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private AssessmentService assessmentService;

    @GetMapping
    public String list(@PathVariable(value = "courseId") Long courseId, Model model) {
        model.addAttribute("lessons", lessonService.getLessonsByCourseId(courseId));
        model.addAttribute("courseId", courseId);
        return "lessons";
    }

    @GetMapping("/add")
    public String addLessonForm(Model model, @PathVariable(value = "courseId") Long courseId) {
        Lessons lesson = new Lessons();
        lesson.setCourseId(courseService.getCourseById(courseId));

        model.addAttribute("lesson", lesson);
        model.addAttribute("allAssessments", assessmentService.getAssessmentsByCourseId(courseId));
        return "lesson-form";
    }

    @GetMapping("/edit/{lessonId}")
    public String editForm(@PathVariable(value = "courseId") Long courseId,
                           @PathVariable(value = "lessonId") Long lessonId,
                           Model model) {
        Lessons lesson = lessonService.getLessonById(lessonId);
        model.addAttribute("lesson", lesson);
        model.addAttribute("allAssessments", assessmentService.getAssessmentsByCourseId(courseId));
        return "lesson-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Lessons lesson,
                       @PathVariable(value = "courseId") Long courseId) {
        lessonService.addOrUpdateLesson(lesson);
        return "redirect:/admin/courses/" + courseId + "/lessons";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id,
                         @PathVariable(value = "courseId") Long courseId) {
        lessonService.deleteLesson(id);
        return "redirect:/admin/courses/" + courseId + "/lessons";
    }
}
