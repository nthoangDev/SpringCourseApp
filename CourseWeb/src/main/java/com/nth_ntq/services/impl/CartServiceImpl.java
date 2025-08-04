/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.services.impl;

import com.nth_ntq.pojo.CartItems;
import com.nth_ntq.pojo.Carts;
import com.nth_ntq.pojo.Courses;
import com.nth_ntq.pojo.Enrollments;
import com.nth_ntq.pojo.Payments;
import com.nth_ntq.pojo.Users;
import com.nth_ntq.repositories.CartItemRepository;
import com.nth_ntq.repositories.CartRepository;
import com.nth_ntq.repositories.CourseRepository;
import com.nth_ntq.repositories.EnrollmentRepository;
import com.nth_ntq.repositories.PaymentRepository;
import com.nth_ntq.repositories.UserRepository;
import com.nth_ntq.services.CartService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author pc
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private CartItemRepository cartItemRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PaymentRepository paymentRepo;

    @Autowired
    private EnrollmentRepository enrollmentRepo;

    @Autowired
    private CourseRepository courseRepo;

    @Override
    public BigDecimal getCartTotal(Long userId) {
        Carts cart = cartRepo.findByUserId(userId);
        List<CartItems> items = cartItemRepo.findByCart(cart);

        return items.stream()
                .map(i -> i.getCourseId().getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @Transactional
    public void finalizeOrder(Map<String, String> vnpParams) {
        Long userId = Long.valueOf(vnpParams.get("vnp_TxnRef")); // bạn cần xác nhận TxnRef chứa gì

        Users user = userRepo.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User không tồn tại trong hệ thống!");
        }
        Carts cart = cartRepo.findByUserId(userId);
        List<CartItems> items = cartItemRepo.findByCart(cart);

        BigDecimal amount = new BigDecimal(vnpParams.get("vnp_Amount")).divide(BigDecimal.valueOf(100));

        Payments payment = new Payments();
        payment.setUserId(user);
        payment.setAmount(amount);
        payment.setMethod("VNPAY");
        payment.setStatus("COMPLETED");
        payment.setTransactionId(vnpParams.get("vnp_TransactionNo"));
        payment.setCreatedAt(new Date());

        paymentRepo.save(payment);

        for (CartItems item : items) {
            item.setPaymentId(payment);
            cartItemRepo.save(item);

            Enrollments e = new Enrollments();
            e.setUserId(user);
            e.setCourseId(item.getCourseId());
            e.setPaymentId(payment);
            e.setEnrolledAt(new Date());

            enrollmentRepo.save(e);
        }

        // Xóa cart nếu muốn, hoặc giữ lại
    }

    @Override
    @Transactional
    public void addToCart(Long userId, Long courseId) {
        // 1 Lấy user
        Users user = userRepo.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("Người dùng không tồn tại!");
        }

        // 2 Lấy cart theo user hoặc tạo mới nếu chưa có
        Carts cart = cartRepo.findByUserId(userId);
        if (cart == null) {
            cart = new Carts();
            cart.setUserId(user);
            cart.setCreatedAt(new Date());
            cartRepo.save(cart);
        }

        // 3 Kiểm tra xem khóa học đã có trong cart chưa
        List<CartItems> items = cartItemRepo.findByCart(cart);
        boolean exists = items.stream()
                .anyMatch(i -> i.getCourseId().getCourseId().equals(courseId));

        if (exists) {
            throw new RuntimeException("Khóa học đã có trong giỏ hàng!");
        }

        // 4 Lấy khóa học và thêm vào cart
        Courses course = courseRepo.getCourseById(courseId);
        if (course == null) {
            throw new RuntimeException("Khóa học không tồn tại!");
        }

        CartItems newItem = new CartItems();
        newItem.setCartId(cart);
        newItem.setCourseId(course);
        newItem.setQuantity(1);
        newItem.setAddedAt(new Date());

        cartItemRepo.save(newItem);
    }

    @Override
    public Carts getCartByUser(Long userId) {
        return cartRepo.findByUserId(userId);
    }

}
