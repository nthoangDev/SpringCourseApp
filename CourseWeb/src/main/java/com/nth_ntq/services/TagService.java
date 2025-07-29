/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nth_ntq.services;

import com.nth_ntq.pojo.Tags;
import java.util.List;

/**
 *
 * @author pc
 */
public interface TagService {
    List<Tags> getTags();
    Tags getTagById(Long id);
}