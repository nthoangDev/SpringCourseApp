/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.pojo;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "assessments")
@NamedQueries({
    @NamedQuery(name = "Assessments.findAll", query = "SELECT a FROM Assessments a"),
    @NamedQuery(name = "Assessments.findByAssessmentId", query = "SELECT a FROM Assessments a WHERE a.assessmentId = :assessmentId"),
    @NamedQuery(name = "Assessments.findByAssessmentType", query = "SELECT a FROM Assessments a WHERE a.assessmentType = :assessmentType"),
    @NamedQuery(name = "Assessments.findByTitle", query = "SELECT a FROM Assessments a WHERE a.title = :title"),
    @NamedQuery(name = "Assessments.findByPoints", query = "SELECT a FROM Assessments a WHERE a.points = :points"),
    @NamedQuery(name = "Assessments.findByAvailableAt", query = "SELECT a FROM Assessments a WHERE a.availableAt = :availableAt"),
    @NamedQuery(name = "Assessments.findByDueAt", query = "SELECT a FROM Assessments a WHERE a.dueAt = :dueAt")})
public class Assessments implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "assessment_id")
    private Long assessmentId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "assessment_type")
    private String assessmentType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "title")
    private String title;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @Column(name = "points")
    private Integer points;
    @Column(name = "available_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date availableAt;
    @Column(name = "due_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueAt;
    @ManyToMany(mappedBy = "assessmentsSet")
    private Set<Lessons> lessonsSet;
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    @ManyToOne(optional = false)
    private Courses courseId;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "assessments")
    private Assignments assignments;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assessments")
    private Set<AssessmentResults> assessmentResultsSet;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "assessments")
    private Tests tests;

    public Assessments() {
    }

    public Assessments(Long assessmentId) {
        this.assessmentId = assessmentId;
    }

    public Assessments(Long assessmentId, String assessmentType, String title) {
        this.assessmentId = assessmentId;
        this.assessmentType = assessmentType;
        this.title = title;
    }

    public Long getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(Long assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
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

    public Set<Lessons> getLessonsSet() {
        return lessonsSet;
    }

    public void setLessonsSet(Set<Lessons> lessonsSet) {
        this.lessonsSet = lessonsSet;
    }

    public Courses getCourseId() {
        return courseId;
    }

    public void setCourseId(Courses courseId) {
        this.courseId = courseId;
    }

    public Assignments getAssignments() {
        return assignments;
    }

    public void setAssignments(Assignments assignments) {
        this.assignments = assignments;
    }

    public Set<AssessmentResults> getAssessmentResultsSet() {
        return assessmentResultsSet;
    }

    public void setAssessmentResultsSet(Set<AssessmentResults> assessmentResultsSet) {
        this.assessmentResultsSet = assessmentResultsSet;
    }

    public Tests getTests() {
        return tests;
    }

    public void setTests(Tests tests) {
        this.tests = tests;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (assessmentId != null ? assessmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Assessments)) {
            return false;
        }
        Assessments other = (Assessments) object;
        if ((this.assessmentId == null && other.assessmentId != null) || (this.assessmentId != null && !this.assessmentId.equals(other.assessmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nth_ntq.pojo.Assessments[ assessmentId=" + assessmentId + " ]";
    }
    
}
