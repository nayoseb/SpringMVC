package com.nhnacademy.nhnmart.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AnswerRegisterDto {

    private Long inquiryId;

    @Length(min = 1, max = 40000)
    private String comment;


    public Long getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(Long inquiryId) {
        this.inquiryId = inquiryId;
    }
}
