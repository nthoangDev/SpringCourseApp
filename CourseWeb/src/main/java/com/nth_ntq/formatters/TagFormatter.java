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
    public Tags parse(String id, Locale locale) throws ParseException {
        Long tagId = Long.valueOf(id);
        return this.tagService.getTags()
                .stream().filter(t -> t.getTagId().equals(tagId)).findFirst().orElse(null);
    }

    @Override
    public String print(Tags tag, Locale locale) {
        return tag.getTagId().toString();
    }
}