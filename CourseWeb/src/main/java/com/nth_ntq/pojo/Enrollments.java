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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "enrollments")
@NamedQueries({
    @NamedQuery(name = "Enrollments.findAll", query = "SELECT e FROM Enrollments e"),
    @NamedQuery(name = "Enrollments.findByEnrollmentId", query = "SELECT e FROM Enrollments e WHERE e.enrollmentId = :enrollmentId"),
    @NamedQuery(name = "Enrollments.findByProgress", query = "SELECT e FROM Enrollments e WHERE e.progress = :progress"),
    @NamedQuery(name = "Enrollments.findByCompleted", query = "SELECT e FROM Enrollments e WHERE e.completed = :completed"),
    @NamedQuery(name = "Enrollments.findByEnrolledAt", query = "SELECT e FROM Enrollments e WHERE e.enrolledAt = :enrolledAt")
})
public class Enrollments implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "enrollment_id")
    private Long enrollmentId;

    @Column(name = "progress")
    private BigDecimal progress;

    @Column(name = "completed")
    private Boolean completed;

    @Column(name = "enrolled_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enrolledAt;

    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    @ManyToOne(optional = false)
    private Courses courseId;

    @JoinColumn(name = "payment_id", referencedColumnName = "payment_id")
    @ManyToOne
    private Payments paymentId;

    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private Users userId;

    // Constructors, hashCode, equals, toString...

    public Long getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(Long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public BigDecimal getProgress() {
        return progress;
    }

    public void setProgress(BigDecimal progress) {
        this.progress = progress;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Date getEnrolledAt() {
        return enrolledAt;
    }

    public void setEnrolledAt(Date enrolledAt) {
        this.enrolledAt = enrolledAt;
    }

    public Courses getCourseId() {
        return courseId;
    }

    public void setCourseId(Courses courseId) {
        this.courseId = courseId;
    }

    public Payments getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Payments paymentId) {
        this.paymentId = paymentId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    // Thực thi kiểm tra completed dựa trên field Boolean completed. Tránh NULL
    public boolean isCompleted() {
        return Boolean.TRUE.equals(this.completed);
    }

    // Getter và setter cho userId và courseId
    public Users getUser() {
        return userId;
    }

    public void setUser(Users user) {
        this.userId = user;
    }
}
