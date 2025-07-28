/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.services;

import com.nth_ntq.pojo.Users;
import java.util.List;

/**
 *
 * @author trung
 */
public interface UserService {
    List<Users> getUsersByKeyword(String kw);
}