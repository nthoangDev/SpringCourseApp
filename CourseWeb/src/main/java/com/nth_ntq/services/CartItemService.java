/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.services;

import java.math.BigDecimal;
import com.nth_ntq.pojo.Carts;
import com.nth_ntq.pojo.CartItems;

/**
 *
 * @author pc
 */
public interface CartItemService {

    BigDecimal getTotal(Carts cart);

    void update(CartItems item);

    void clearCart(Long cartId);

    CartItems getById(Long itemId);

    void delete(Long itemId);
}
