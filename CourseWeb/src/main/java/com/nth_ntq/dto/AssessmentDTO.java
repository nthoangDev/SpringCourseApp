package com.nth_ntq.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author pc
 */
public class AssessmentDTO {

    private Long assessmentId;

    @NotBlank
    private String title;

    private String description;

    private Integer points;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date availableAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date dueAt;

    @NotNull
    private String assessmentType; // "ASSIGNMENT" hoặc "TEST"

    @NotNull
    private Long courseId; // để convert ra Courses trong controller

    // Trường riêng cho từng loại
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date dueDate;
    // Dùng cho ASSIGNMENT
    private Integer durationMinutes; // Dùng cho TEST

    // === GETTERS AND SETTERS ===
    public Long getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(Long assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Date getAvailableAt() {
        return availableAt;
    }

    public void setAvailableAt(Date availableAt) {
        this.availableAt = availableAt;
    }

    public Date getDueAt() {
        return dueAt;
    }

    public void setDueAt(Date dueAt) {
        this.dueAt = dueAt;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
}