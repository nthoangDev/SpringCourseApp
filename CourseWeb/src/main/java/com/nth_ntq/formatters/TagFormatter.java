/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.formatters;

import com.nth_ntq.pojo.Tags;
import com.nth_ntq.services.TagService;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

/**
 *
 * @author pc
 */
@Component
public class TagFormatter implements Formatter<Tags> {

    @Autowired
    private TagService tagService;

    @Override
    public Tags parse(String tagId, Locale locale) throws ParseException {
        Tags tag = new Tags();
        tag.setTagId(Long.valueOf(tagId)); // Gán ID trực tiếp
        return tag;
    }

    @Override
    public String print(Tags tag, Locale locale) {
        return tag.getTagId().toString();
    }
}
