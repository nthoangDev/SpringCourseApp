/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.services;

import com.nth_ntq.dto.ProgressDTO;

/**
 *
 * @author trung
 */
public interface ProgressService {
    ProgressDTO getProgress(Long userId, Long courseId);
}