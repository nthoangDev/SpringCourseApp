/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.pojo;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 * @author pc
 */
@Embeddable
public class AssessmentResultsPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "assessment_id")
    private long assessmentId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_id")
    private long userId;

    public AssessmentResultsPK() {
    }

    public AssessmentResultsPK(long assessmentId, long userId) {
        this.assessmentId = assessmentId;
        this.userId = userId;
    }

    public long getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(long assessmentId) {
        this.assessmentId = assessmentId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) assessmentId;
        hash += (int) userId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AssessmentResultsPK)) {
            return false;
        }
        AssessmentResultsPK other = (AssessmentResultsPK) object;
        if (this.assessmentId != other.assessmentId) {
            return false;
        }
        if (this.userId != other.userId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nth_ntq.pojo.AssessmentResultsPK[ assessmentId=" + assessmentId + ", userId=" + userId + " ]";
    }
    
}
