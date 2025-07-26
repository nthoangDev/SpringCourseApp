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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
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
@Table(name = "assessments")
@NamedQueries({
    @NamedQuery(name = "Assessments.findAll", query = "SELECT a FROM Assessments a"),
    @NamedQuery(name = "Assessments.findById", query = "SELECT a FROM Assessments a WHERE a.id = :id"),
    @NamedQuery(name = "Assessments.findByType", query = "SELECT a FROM Assessments a WHERE a.type = :type")})
public class Assessments implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "type")
    private String type;
    @ManyToMany(mappedBy = "assessmentsSet")
    private Set<Users> usersSet;
    @ManyToMany(mappedBy = "assessmentsSet")
    private Set<Lessons> lessonsSet;
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Courses courseId;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "assessments")
    private Assignments assignments;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "assessments")
    private Tests tests;

    public Assessments() {
    }

    public Assessments(Long id) {
        this.id = id;
    }

    public Assessments(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Users> getUsersSet() {
        return usersSet;
    }

    public void setUsersSet(Set<Users> usersSet) {
        this.usersSet = usersSet;
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

    public Tests getTests() {
        return tests;
    }

    public void setTests(Tests tests) {
        this.tests = tests;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Assessments)) {
            return false;
        }
        Assessments other = (Assessments) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nth_ntq.pojo.Assessments[ id=" + id + " ]";
    }
    
}
