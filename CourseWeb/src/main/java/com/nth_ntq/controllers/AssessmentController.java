/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.controllers;

import com.nth_ntq.dto.AssessmentDTO;
import com.nth_ntq.services.AssessmentService;
import com.nth_ntq.services.CourseService;
import com.nth_ntq.pojo.Assessments;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author pc
 */
@Controller
@RequestMapping("/admin/courses/{courseId}/assessments")
public class AssessmentController {

    @Autowired
    private AssessmentService assessmentService;

    @Autowired
    private CourseService courseService;

    @GetMapping
    public String list(@PathVariable("courseId") Long courseId, Model model) {
        model.addAttribute("assessments", assessmentService.getAssessmentsByCourseId(courseId));
        model.addAttribute("courseId", courseId);
        return "assessments";
    }

    @GetMapping("/add")
    public String addForm(@PathVariable("courseId") Long courseId, Model model) {
        AssessmentDTO dto = new AssessmentDTO();
        dto.setCourseId(courseId);
        model.addAttribute("assessmentDTO", dto);
        return "assessment-form";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("courseId") Long courseId,
            @PathVariable("id") Long id,
            Model model) {
        Assessments a = assessmentService.getById(id);

        if (a == null) {
            return "redirect:/admin/courses/" + courseId + "/assessments";
        }

        AssessmentDTO dto = new AssessmentDTO();
        dto.setAssessmentId(a.getAssessmentId());
        dto.setTitle(a.getTitle());
        dto.setDescription(a.getDescription());
        dto.setPoints(a.getPoints());
        dto.setAvailableAt(a.getAvailableAt());
        dto.setDueAt(a.getDueAt());
        dto.setAssessmentType(a.getAssessmentType());
        dto.setCourseId(a.getCourseId().getCourseId());

        // Gán dữ liệu riêng theo loại
        if ("ASSIGNMENT".equalsIgnoreCase(a.getAssessmentType()) && a.getAssignments() != null) {
            dto.setDueDate(a.getAssignments().getDueDate());
        } else if ("TEST".equalsIgnoreCase(a.getAssessmentType()) && a.getTests() != null) {
            dto.setDurationMinutes(a.getTests().getDurationMinutes());
        }

        model.addAttribute("assessmentDTO", dto);
        model.addAttribute("courseId", courseId);
        return "assessment-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("assessmentDTO") @Valid AssessmentDTO dto,
            @PathVariable("courseId") Long courseId) {
        assessmentService.saveAssessment(dto);
        return "redirect:/admin/courses/" + courseId + "/assessments";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("courseId") Long courseId,
            @PathVariable("id") Long id) {
        assessmentService.delete(id);
        return "redirect:/admin/courses/" + courseId + "/assessments";
    }

}
