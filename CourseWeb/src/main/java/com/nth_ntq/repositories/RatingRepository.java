/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nth_ntq.repositories;

import java.util.Map;

/**
 *
 * @author pc
 */
public interface RatingRepository {
    Map<String, Object> getRatingSummary(Long courseId);
}