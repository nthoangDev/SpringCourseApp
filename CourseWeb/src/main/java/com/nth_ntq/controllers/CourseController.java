/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.controllers;

import com.nth_ntq.pojo.Courses;
import com.nth_ntq.services.CourseService;
import com.nth_ntq.services.TagService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author pc
 */
@Controller
@RequestMapping("/admin/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private TagService tagService;

    @GetMapping
    public String list(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("courses", courseService.getCourses(params));
        return "courses";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("course", new Courses());
        model.addAttribute("allTags", tagService.getTags());
        return "course-form";
    }

    @GetMapping("/{courseId}")
    public String editForm(Model model,  @PathVariable(value = "courseId") Long id) {
        model.addAttribute("course", courseService.getCourseById(id));
        model.addAttribute("allTags", tagService.getTags());
        return "course-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Courses course) {
        courseService.addOrUpdateCourse(course);
        return "redirect:/admin/courses";
    }

    @GetMapping("/delete/{courseId}")
    public String delete(@PathVariable(value = "courseId") Long id) {
        courseService.deleteCourse(id);
        return "redirect:/admin/courses";
    }
}
