/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.dto;

/**
 *
 * @author trung
 */
public class ProgressDTO {
    private long completedLessons;
    private long totalLessons;
    private double percent;
    private boolean completed;

    public ProgressDTO(long completedLessons, long totalLessons,
                       double percent, boolean completed) {
        this.completedLessons = completedLessons;
        this.totalLessons     = totalLessons;
        this.percent          = percent;
        this.completed        = completed;
    }

    /**
     * @return the completedLessons
     */
    public long getCompletedLessons() {
        return completedLessons;
    }

    /**
     * @param completedLessons the completedLessons to set
     */
    public void setCompletedLessons(long completedLessons) {
        this.completedLessons = completedLessons;
    }

    /**
     * @return the totalLessons
     */
    public long getTotalLessons() {
        return totalLessons;
    }

    /**
     * @param totalLessons the totalLessons to set
     */
    public void setTotalLessons(long totalLessons) {
        this.totalLessons = totalLessons;
    }

    /**
     * @return the percent
     */
    public double getPercent() {
        return percent;
    }

    /**
     * @param percent the percent to set
     */
    public void setPercent(double percent) {
        this.percent = percent;
    }

    /**
     * @return the completed
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * @param completed the completed to set
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
}
