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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "courses")
@NamedQueries({
    @NamedQuery(name = "Courses.findAll", query = "SELECT c FROM Courses c"),
    @NamedQuery(name = "Courses.findByCourseId", query = "SELECT c FROM Courses c WHERE c.courseId = :courseId"),
    @NamedQuery(name = "Courses.findByTitle", query = "SELECT c FROM Courses c WHERE c.title = :title"),
    @NamedQuery(name = "Courses.findByImageUrl", query = "SELECT c FROM Courses c WHERE c.imageUrl = :imageUrl"),
    @NamedQuery(name = "Courses.findByLevel", query = "SELECT c FROM Courses c WHERE c.level = :level"),
    @NamedQuery(name = "Courses.findByPrice", query = "SELECT c FROM Courses c WHERE c.price = :price"),
    @NamedQuery(name = "Courses.findByDuration", query = "SELECT c FROM Courses c WHERE c.duration = :duration"),
    @NamedQuery(name = "Courses.findByStatus", query = "SELECT c FROM Courses c WHERE c.status = :status"),
    @NamedQuery(name = "Courses.findByDeleted", query = "SELECT c FROM Courses c WHERE c.deleted = :deleted"),
    @NamedQuery(name = "Courses.findByCreatedAt", query = "SELECT c FROM Courses c WHERE c.createdAt = :createdAt")})
public class Courses implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "course_id")
    private Long courseId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "title")
    private String title;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @Size(max = 500)
    @Column(name = "image_url")
    private String imageUrl;
    @Size(max = 12)
    @Column(name = "level")
    private String level;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private BigDecimal price;
    @Size(max = 50)
    @Column(name = "duration")
    private String duration;
    @Size(max = 8)
    @Column(name = "status")
    private String status;
    @Column(name = "deleted")
    private Boolean deleted;
    @Lob
    @Size(max = 65535)
    @Column(name = "instructor_note")
    private String instructorNote;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinTable(name = "course_tags", joinColumns = {
        @JoinColumn(name = "course_id", referencedColumnName = "course_id")}, inverseJoinColumns = {
        @JoinColumn(name = "tag_id", referencedColumnName = "tag_id")})
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
    private Set<CourseTeachers> courseTeachersSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseId")
    private Set<Certificates> certificatesSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseId")
    private Set<Ratings> ratingsSet;
    @OneToMany(mappedBy = "courseId")
    private Set<Notifications> notificationsSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseId")
    private Set<Lessons> lessonsSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseId")
    private Set<Likes> likesSet;
    @Transient
    private MultipartFile file;

    public Courses() {
    }

    public Courses(Long courseId) {
        this.courseId = courseId;
    }

    public Courses(Long courseId, String title) {
        this.courseId = courseId;
        this.title = title;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getInstructorNote() {
        return instructorNote;
    }

    public void setInstructorNote(String instructorNote) {
        this.instructorNote = instructorNote;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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

    public Set<CourseTeachers> getCourseTeachersSet() {
        return courseTeachersSet;
    }

    public void setCourseTeachersSet(Set<CourseTeachers> courseTeachersSet) {
        this.courseTeachersSet = courseTeachersSet;
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
        hash += (courseId != null ? courseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Courses)) {
            return false;
        }
        Courses other = (Courses) object;
        if ((this.courseId == null && other.courseId != null) || (this.courseId != null && !this.courseId.equals(other.courseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nth_ntq.pojo.Courses[ courseId=" + courseId + " ]";
    }

    /**
     * @return the file
     */
    public MultipartFile getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(MultipartFile file) {
        this.file = file;
    }

}
