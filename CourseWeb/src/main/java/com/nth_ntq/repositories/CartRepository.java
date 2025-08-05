/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nth_ntq.repositories;

import com.nth_ntq.pojo.Carts;

/**
 *
 * @author pc
 */
public interface CartRepository {

    Carts findByUserId(Long userId);

    Carts save(Carts cart);

}
