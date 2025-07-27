/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.pojo;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "lessons")
@NamedQueries({
    @NamedQuery(name = "Lessons.findAll", query = "SELECT l FROM Lessons l"),
    @NamedQuery(name = "Lessons.findByLessonId", query = "SELECT l FROM Lessons l WHERE l.lessonId = :lessonId"),
    @NamedQuery(name = "Lessons.findByTitle", query = "SELECT l FROM Lessons l WHERE l.title = :title"),
    @NamedQuery(name = "Lessons.findByVideoUrl", query = "SELECT l FROM Lessons l WHERE l.videoUrl = :videoUrl"),
    @NamedQuery(name = "Lessons.findByOrderIndex", query = "SELECT l FROM Lessons l WHERE l.orderIndex = :orderIndex"),
    @NamedQuery(name = "Lessons.findByIsPreview", query = "SELECT l FROM Lessons l WHERE l.isPreview = :isPreview")})
public class Lessons implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "lesson_id")
    private Long lessonId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "title")
    private String title;
    @Lob
    @Size(max = 65535)
    @Column(name = "content")
    private String content;
    @Size(max = 500)
    @Column(name = "video_url")
    private String videoUrl;
    @Column(name = "order_index")
    private Integer orderIndex;
    @Column(name = "is_preview")
    private Boolean isPreview;
    @JoinTable(name = "lesson_assessment", joinColumns = {
        @JoinColumn(name = "lesson_id", referencedColumnName = "lesson_id")}, inverseJoinColumns = {
        @JoinColumn(name = "assessment_id", referencedColumnName = "assessment_id")})
    @ManyToMany
    private Set<Assessments> assessmentsSet;
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    @ManyToOne(optional = false)
    private Courses courseId;

    public Lessons() {
    }

    public Lessons(Long lessonId) {
        this.lessonId = lessonId;
    }

    public Lessons(Long lessonId, String title) {
        this.lessonId = lessonId;
        this.title = title;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Boolean getIsPreview() {
        return isPreview;
    }

    public void setIsPreview(Boolean isPreview) {
        this.isPreview = isPreview;
    }

    public Set<Assessments> getAssessmentsSet() {
        return assessmentsSet;
    }

    public void setAssessmentsSet(Set<Assessments> assessmentsSet) {
        this.assessmentsSet = assessmentsSet;
    }

    public Courses getCourseId() {
        return courseId;
    }

    public void setCourseId(Courses courseId) {
        this.courseId = courseId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lessonId != null ? lessonId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lessons)) {
            return false;
        }
        Lessons other = (Lessons) object;
        if ((this.lessonId == null && other.lessonId != null) || (this.lessonId != null && !this.lessonId.equals(other.lessonId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nth_ntq.pojo.Lessons[ lessonId=" + lessonId + " ]";
    }
    
}
