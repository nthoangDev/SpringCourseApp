/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.formatters;

import com.nth_ntq.pojo.Courses;
import com.nth_ntq.services.CourseService;
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
public class CourseFormatter implements Formatter<Courses> {

    @Autowired
    private CourseService courseService;

    @Override
    public Courses parse(String text, Locale locale) throws ParseException {
        Courses c = new Courses();
        c.setCourseId(Long.valueOf(text));
        return c;
    }

    @Override
    public String print(Courses object, Locale locale) {
        return object.getCourseId().toString();
    }
}
