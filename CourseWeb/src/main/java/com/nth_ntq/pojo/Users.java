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
@Table(name = "users")
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findById", query = "SELECT u FROM Users u WHERE u.id = :id"),
    @NamedQuery(name = "Users.findByUsername", query = "SELECT u FROM Users u WHERE u.username = :username"),
    @NamedQuery(name = "Users.findByPassword", query = "SELECT u FROM Users u WHERE u.password = :password"),
    @NamedQuery(name = "Users.findByEmail", query = "SELECT u FROM Users u WHERE u.email = :email"),
    @NamedQuery(name = "Users.findByAvatar", query = "SELECT u FROM Users u WHERE u.avatar = :avatar")})
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "password")
    private String password;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "email")
    private String email;
    @Size(max = 255)
    @Column(name = "avatar")
    private String avatar;
    @ManyToMany(mappedBy = "usersSet")
    private Set<Courses> coursesSet;
    @JoinTable(name = "assessment_results", joinColumns = {
        @JoinColumn(name = "user_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "assessment_id", referencedColumnName = "id")})
    @ManyToMany
    private Set<Assessments> assessmentsSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<Comments> commentsSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<Payments> paymentsSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<Enrollments> enrollmentsSet;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "userId")
    private Carts carts;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<Certificates> certificatesSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<Ratings> ratingsSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<Notifications> notificationsSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<Likes> likesSet;

    public Users() {
    }

    public Users(Long id) {
        this.id = id;
    }

    public Users(Long id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Set<Courses> getCoursesSet() {
        return coursesSet;
    }

    public void setCoursesSet(Set<Courses> coursesSet) {
        this.coursesSet = coursesSet;
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

    public Set<Payments> getPaymentsSet() {
        return paymentsSet;
    }

    public void setPaymentsSet(Set<Payments> paymentsSet) {
        this.paymentsSet = paymentsSet;
    }

    public Set<Enrollments> getEnrollmentsSet() {
        return enrollmentsSet;
    }

    public void setEnrollmentsSet(Set<Enrollments> enrollmentsSet) {
        this.enrollmentsSet = enrollmentsSet;
    }

    public Carts getCarts() {
        return carts;
    }

    public void setCarts(Carts carts) {
        this.carts = carts;
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
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nth_ntq.pojo.Users[ id=" + id + " ]";
    }
    
}
