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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
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
@Table(name = "courses")
@NamedQueries({
    @NamedQuery(name = "Courses.findAll", query = "SELECT c FROM Courses c"),
    @NamedQuery(name = "Courses.findById", query = "SELECT c FROM Courses c WHERE c.id = :id"),
    @NamedQuery(name = "Courses.findByTitle", query = "SELECT c FROM Courses c WHERE c.title = :title")})
public class Courses implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "title")
    private String title;
    @JoinTable(name = "course_teacher", joinColumns = {
        @JoinColumn(name = "course_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "user_id", referencedColumnName = "id")})
    @ManyToMany
    private Set<Users> usersSet;
    @JoinTable(name = "course_tag", joinColumns = {
        @JoinColumn(name = "course_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "tag_id", referencedColumnName = "id")})
    @ManyToMany
    private Set<Tags> tagsSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseId")
    private Set<Assessments> assessmentsSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseId")
    private Set<Comments> commentsSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseId")
    private Set<CartItems> cartItemsSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseId")
    private Set<Enrollments> enrollmentsSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseId")
    private Set<Certificates> certificatesSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseId")
    private Set<Ratings> ratingsSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseId")
    private Set<Notifications> notificationsSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseId")
    private Set<Lessons> lessonsSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseId")
    private Set<Likes> likesSet;

    public Courses() {
    }

    public Courses(Long id) {
        this.id = id;
    }

    public Courses(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Users> getUsersSet() {
        return usersSet;
    }

    public void setUsersSet(Set<Users> usersSet) {
        this.usersSet = usersSet;
    }

    public Set<Tags> getTagsSet() {
        return tagsSet;
    }

    public void setTagsSet(Set<Tags> tagsSet) {
        this.tagsSet = tagsSet;
    }

    public Set<Assessments> getAssessmentsSet() {
        return assessmentsSet;
    }

    public void setAssessmentsSet(Set<Assessments> assessmentsSet) {
        this.assessmentsSet = assessmentsSet;
    }

    public Set<Comments> getCommentsSet() {
        return commentsSet;
    }

    public void setCommentsSet(Set<Comments> commentsSet) {
        this.commentsSet = commentsSet;
    }

    public Set<CartItems> getCartItemsSet() {
        return cartItemsSet;
    }

    public void setCartItemsSet(Set<CartItems> cartItemsSet) {
        this.cartItemsSet = cartItemsSet;
    }

    public Set<Enrollments> getEnrollmentsSet() {
        return enrollmentsSet;
    }

    public void setEnrollmentsSet(Set<Enrollments> enrollmentsSet) {
        this.enrollmentsSet = enrollmentsSet;
    }

    public Set<Certificates> getCertificatesSet() {
        return certificatesSet;
    }

    public void setCertificatesSet(Set<Certificates> certificatesSet) {
        this.certificatesSet = certificatesSet;
    }

    public Set<Ratings> getRatingsSet() {
        return ratingsSet;
    }

    public void setRatingsSet(Set<Ratings> ratingsSet) {
        this.ratingsSet = ratingsSet;
    }

    public Set<Notifications> getNotificationsSet() {
        return notificationsSet;
    }

    public void setNotificationsSet(Set<Notifications> notificationsSet) {
        this.notificationsSet = notificationsSet;
    }

    public Set<Lessons> getLessonsSet() {
        return lessonsSet;
    }

    public void setLessonsSet(Set<Lessons> lessonsSet) {
        this.lessonsSet = lessonsSet;
    }

    public Set<Likes> getLikesSet() {
        return likesSet;
    }

    public void setLikesSet(Set<Likes> likesSet) {
        this.likesSet = likesSet;
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
        if (!(object instanceof Courses)) {
            return false;
        }
        Courses other = (Courses) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nth_ntq.pojo.Courses[ id=" + id + " ]";
    }
    
}
