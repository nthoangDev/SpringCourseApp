/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nth_ntq.repositories;

/**
 *
 * @author pc
 */
public interface CartItemRepository {
    boolean existsByUserAndCourse(Long userId, Long courseId);
}
