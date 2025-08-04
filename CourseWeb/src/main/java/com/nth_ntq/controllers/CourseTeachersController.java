/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.controllers;

import com.nth_ntq.pojo.CourseTeachers;
import com.nth_ntq.pojo.Courses;
import com.nth_ntq.pojo.Users;
import com.nth_ntq.services.CourseService;
import com.nth_ntq.services.CourseTeachersService;
import com.nth_ntq.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 *
 * @author trung
 */
@Controller
@RequestMapping("/admin/courses")
public class CourseTeachersController {
    private final CourseService courseService;
    private final CourseTeachersService ctService;
    private final UserService userService;

    public CourseTeachersController(CourseService courseService,
                                    CourseTeachersService ctService,
                                    UserService userService) {
        this.courseService = courseService;
        this.ctService = ctService;
        this.userService = userService;
    }

    @GetMapping("/{id}/add-teacher")
    public String showAddForm(@PathVariable("id") Long courseId, Model model) {
        Courses course = courseService.getCourseById(courseId);
        List<Users> teachers = userService.getAllTeachers(); // implement in UserService
        List<CourseTeachers> assigned = ctService.getByCourse(courseId);

        model.addAttribute("course", course);
        model.addAttribute("teachers", teachers);
        model.addAttribute("assigned", assigned);
        return "admin/course-add-teacher";
    }

    @PostMapping("/{id}/add-teacher")
    public String doAdd(@PathVariable("id") Long courseId,
                        @RequestParam("teacherId") Long teacherId,
                        @RequestParam(value="roleDesc", defaultValue="") String roleDesc) {
        ctService.assign(courseId, teacherId, roleDesc);
        return "redirect:/admin/courses/" + courseId + "/add-teacher";
    }
}
