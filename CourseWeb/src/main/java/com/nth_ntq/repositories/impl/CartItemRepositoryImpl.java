/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.repositories.impl;

import com.nth_ntq.pojo.Carts;
import com.nth_ntq.pojo.CartItems;
import com.nth_ntq.repositories.CartItemRepository;
import jakarta.persistence.criteria.*;
import java.util.Iterator;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * @author pc
 */
@Repository
@Transactional
public class CartItemRepositoryImpl implements CartItemRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<CartItems> findByCart(Carts cart) {
        Session s = factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<CartItems> cq = cb.createQuery(CartItems.class);
        Root<CartItems> root = cq.from(CartItems.class);
        cq.select(root).where(cb.equal(root.get("cartId"), cart));
        return s.createQuery(cq).getResultList();
    }

    @Override
    public CartItems save(CartItems item) {
        Session s = factory.getObject().getCurrentSession();
        s.merge(item);
        return item;
    }

    @Override
    @Transactional
    public void deleteByCartId(Long cartId) {
        Session session = factory.getObject().getCurrentSession();
        Carts cart = session.get(Carts.class, cartId);

        if (cart != null && cart.getCartItemsSet() != null) {
            for (Iterator<CartItems> it = cart.getCartItemsSet().iterator(); it.hasNext();) {
                CartItems item = it.next();

                // ❗ Chỉ xóa nếu chưa thanh toán
                if (item.getPaymentId() == null) {
                    it.remove();              // Xóa khỏi Set để tránh cascade
                    session.remove(item);     // Xóa khỏi DB
                }
            }
        }
    }

    @Override
    public CartItems findById(Long itemId) {
        Session session = factory.getObject().getCurrentSession();
        return session.get(CartItems.class, itemId);
    }

    @Override
    public void deleteById(Long itemId) {
        Session session = factory.getObject().getCurrentSession();
        CartItems item = session.get(CartItems.class, itemId);

        if (item != null && item.getPaymentId() == null) {
            session.remove(item);
        }
    }

}
