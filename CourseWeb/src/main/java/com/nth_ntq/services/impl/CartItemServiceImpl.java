/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.services.impl;

import com.nth_ntq.pojo.CartItems;
import com.nth_ntq.pojo.Carts;
import com.nth_ntq.repositories.CartItemRepository;
import com.nth_ntq.services.CartItemService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author pc
 */
@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepo;

    @Override
    public BigDecimal getTotal(Carts cart) {
        List<CartItems> items = cartItemRepo.findByCart(cart);
        return items.stream()
                .map(i -> i.getCourseId().getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public void update(CartItems item) {
        cartItemRepo.save(item); // Hibernate saveOrUpdate
    }

    @Override
    public void clearCart(Long cartId) {
        cartItemRepo.deleteByCartId(cartId);
    }

    @Override
    public CartItems getById(Long itemId) {
        return cartItemRepo.findById(itemId);
    }

    @Override
    public void delete(Long itemId) {
        cartItemRepo.deleteById(itemId);
    }
}
