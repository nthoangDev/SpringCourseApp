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
import jakarta.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "course_teachers")
@NamedQueries({
    @NamedQuery(name = "CourseTeachers.findAll", query = "SELECT c FROM CourseTeachers c"),
    @NamedQuery(name = "CourseTeachers.findByCourseTeacherId", query = "SELECT c FROM CourseTeachers c WHERE c.courseTeacherId = :courseTeacherId"),
    @NamedQuery(name = "CourseTeachers.findByRoleDesc", query = "SELECT c FROM CourseTeachers c WHERE c.roleDesc = :roleDesc")})
public class CourseTeachers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "course_teacher_id")
    private Long courseTeacherId;
    @Size(max = 100)
    @Column(name = "role_desc")
    private String roleDesc;
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    @ManyToOne(optional = false)
    private Courses courseId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private Users userId;

    public CourseTeachers() {
    }

    public CourseTeachers(Long courseTeacherId) {
        this.courseTeacherId = courseTeacherId;
    }

    public Long getCourseTeacherId() {
        return courseTeacherId;
    }

    public void setCourseTeacherId(Long courseTeacherId) {
        this.courseTeacherId = courseTeacherId;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public Courses getCourseId() {
        return courseId;
    }

    public void setCourseId(Courses courseId) {
        this.courseId = courseId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (courseTeacherId != null ? courseTeacherId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CourseTeachers)) {
            return false;
        }
        CourseTeachers other = (CourseTeachers) object;
        if ((this.courseTeacherId == null && other.courseTeacherId != null) || (this.courseTeacherId != null && !this.courseTeacherId.equals(other.courseTeacherId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nth_ntq.pojo.CourseTeachers[ courseTeacherId=" + courseTeacherId + " ]";
    }
    
}
