/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.controllers;

import com.nth_ntq.pojo.Users;
import com.nth_ntq.services.TeacherService;
import com.nth_ntq.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author trung
 */
@Controller
@RequestMapping("/admin/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private UserService userService;

    // Hiển thị form phân quyền: list các User chưa là teacher
    @GetMapping("/assign")
    public String assignForm(
            @RequestParam(name = "kw", required = false) String kw,
            Model model) {
        model.addAttribute("users", teacherService.getUsersByRole("USER"));

        model.addAttribute("kw", kw);
        return "admin/teacher-assign";
    }

    @GetMapping
    public String listTeachers(
            @RequestParam(name = "kw", required = false) String kw,
            Model model) {
        model.addAttribute("kw", kw);
        model.addAttribute("teachers",
                teacherService.getTeachersByKeyword(kw));
        return "admin/teachers";
    }

    // Xử lý thăng chức
    @PostMapping("/assign")
    public String promote(@RequestParam("userId") Long userId) {
        teacherService.promoteToTeacher(userId);
        return "redirect:/admin/teachers";
    }

    // Giáng chức về USER
    @GetMapping("/demote/{id}")
    public String demote(@PathVariable("id") Long userId) {
        teacherService.demoteToUser(userId);
        return "redirect:/admin/teachers";
    }
}
