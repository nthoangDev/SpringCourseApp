/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nth_ntq.formatters;

import com.nth_ntq.pojo.Assessments;
import com.nth_ntq.services.AssessmentService;
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
public class AssessmentFormatter implements Formatter<Assessments> {

    @Autowired
    private AssessmentService assessmentService;

//    @Override
//    public Assessments parse(String assessmentId, Locale locale) throws ParseException {
//        Assessments a = new Assessments();
//        a.setAssessmentId(Long.valueOf(assessmentId));
//        return a;
//    }
    @Override
    public Assessments parse(String text, Locale locale) throws ParseException {
        Assessments a = new Assessments();
        a.setAssessmentId(Long.valueOf(text));
        return a;
    }

    @Override
    public String print(Assessments object, Locale locale) {
        return object.getAssessmentId().toString();
    }
}
