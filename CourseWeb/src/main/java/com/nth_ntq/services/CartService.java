/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nth_ntq.services;

import com.nth_ntq.pojo.Carts;
import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author pc
 */
public interface CartService {

    void addToCart(Long userId, Long courseId);

    BigDecimal getCartTotal(Long userId);

    void finalizeOrder(Map<String, String> vnpParams);

    Carts getCartByUser(Long userId);
}
