/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "assessment_results")
@NamedQueries({
    @NamedQuery(name = "AssessmentResults.findAll", query = "SELECT a FROM AssessmentResults a"),
    @NamedQuery(name = "AssessmentResults.findByAssessmentId", query = "SELECT a FROM AssessmentResults a WHERE a.assessmentResultsPK.assessmentId = :assessmentId"),
    @NamedQuery(name = "AssessmentResults.findByUserId", query = "SELECT a FROM AssessmentResults a WHERE a.assessmentResultsPK.userId = :userId"),
    @NamedQuery(name = "AssessmentResults.findByScore", query = "SELECT a FROM AssessmentResults a WHERE a.score = :score"),
    @NamedQuery(name = "AssessmentResults.findByCompletedAt", query = "SELECT a FROM AssessmentResults a WHERE a.completedAt = :completedAt")})
public class AssessmentResults implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AssessmentResultsPK assessmentResultsPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "score")
    private BigDecimal score;
    @Lob
    @Size(max = 65535)
    @Column(name = "feedback")
    private String feedback;
    @Column(name = "completed_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date completedAt;
    @JoinColumn(name = "assessment_id", referencedColumnName = "assessment_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Assessments assessments;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users;

    public AssessmentResults() {
    }

    public AssessmentResults(AssessmentResultsPK assessmentResultsPK) {
        this.assessmentResultsPK = assessmentResultsPK;
    }

    public AssessmentResults(long assessmentId, long userId) {
        this.assessmentResultsPK = new AssessmentResultsPK(assessmentId, userId);
    }

    public AssessmentResultsPK getAssessmentResultsPK() {
        return assessmentResultsPK;
    }

    public void setAssessmentResultsPK(AssessmentResultsPK assessmentResultsPK) {
        this.assessmentResultsPK = assessmentResultsPK;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public Assessments getAssessments() {
        return assessments;
    }

    public void setAssessments(Assessments assessments) {
        this.assessments = assessments;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (assessmentResultsPK != null ? assessmentResultsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AssessmentResults)) {
            return false;
        }
        AssessmentResults other = (AssessmentResults) object;
        if ((this.assessmentResultsPK == null && other.assessmentResultsPK != null) || (this.assessmentResultsPK != null && !this.assessmentResultsPK.equals(other.assessmentResultsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nth_ntq.pojo.AssessmentResults[ assessmentResultsPK=" + assessmentResultsPK + " ]";
    }
    
}
