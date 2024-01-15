package com.nhnacademy.nhnmart.dto;

import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Data
public class InquiryRegisterDto {
    @Length(min = 2, max = 200)
    private String title;

    @NotBlank
    private String category;

    @Length(max = 40000)
    private String content;

    private String author;

    private List<MultipartFile> imageFiles;
}
