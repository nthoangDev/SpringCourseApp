/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.repositories;

import com.nth_ntq.pojo.Notifications;
import java.util.List;

/**
 *
 * @author trung
 */
public interface NotificationRepository {
    /** Lưu hoặc cập nhật notification */
    void save(Notifications notification);

    /** Đếm số notification kiểu REMINDER (chưa đọc) cho user */
    long countReminders(Long userId);

    /** Lấy danh sách notification của user, mới nhất trước */
    List<Notifications> findByUser(Long userId);
}
