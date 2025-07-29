/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.services.impl;

import com.nth_ntq.pojo.Tags;
import com.nth_ntq.repositories.TagRepository;
import com.nth_ntq.services.TagService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author pc
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepo;

    @Override
    public List<Tags> getTags() {
        return this.tagRepo.getTags();
    }

    @Override
    public Tags getTagById(Long id) {
        return tagRepo.findById(id);
    }
}
