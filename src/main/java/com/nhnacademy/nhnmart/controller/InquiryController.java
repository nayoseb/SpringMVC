package com.nhnacademy.nhnmart.controller;

import com.nhnacademy.nhnmart.domain.Inquiry;
import com.nhnacademy.nhnmart.dto.InquiryRegisterDto;
import com.nhnacademy.nhnmart.reposiyory.InquiryRepository;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/cs")
public class InquiryController {
    private final InquiryRepository inquiryRepository;




    public InquiryController(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    @GetMapping("/inquiries/new")
    public String showInquiryForm(Model model) {
        model.addAttribute("inquiryRegisterDto", new InquiryRegisterDto());
        return "inquiryForm";
    }

    @PostMapping("/inquiries/post")
    public String registerInquiry(@Valid InquiryRegisterDto inquiryRegisterDto,
                                  BindingResult bindingResult,
                                  HttpServletRequest request,
                                  HttpSession httpSession,
                                  Model model) {
        if (bindingResult.hasErrors()) {
            return "inquiryForm"; // 유효성 검사 실패 시, 폼으로 돌아감
        }

        String uploadPath = request.getServletContext().getRealPath("/resources/images");
        String username = (String) httpSession.getAttribute("username");

        Inquiry inquiry = Inquiry.builder()
                .title(inquiryRegisterDto.getTitle())
                .category(inquiryRegisterDto.getCategory())
                .content(inquiryRegisterDto.getContent())
                .creationDate(LocalDateTime.now())
                .authorId(username)
                .attachments(new ArrayList<>())
                .isAnswered(false)
                .build();

        List<String> filePaths = processUploadedFiles(inquiryRegisterDto.getImageFiles(), uploadPath);
        inquiry.setAttachments(filePaths);

        inquiryRepository.save(inquiry);
        return "redirect:/cs/inquiries";
    }


    private List<String> processUploadedFiles(List<MultipartFile> imageFiles, String uploadPath) {
        return imageFiles.stream().map(file -> {
            if (!file.isEmpty()) {
                try {
                    File uploadDir = new File(uploadPath); // 변경된 부분
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs(); // 디렉토리가 없으면 생성
                    }
                    String originalFilename = file.getOriginalFilename();
                    File dest = new File(uploadPath, originalFilename); // 변경된 부분
                    file.transferTo(dest);
                    return dest.getAbsolutePath();
                } catch (IOException e) {
                    throw new RuntimeException("파일 업로드 처리 실패", e);
                }
            }
            return null;
        }).collect(Collectors.toList());
    }

    @GetMapping("/inquiry/{id}")
    public String showInquiryDetail(@PathVariable Long id, Model model) {
        Inquiry inquiry = inquiryRepository.findById(id);
        if (inquiry != null) {
            model.addAttribute("inquiry", inquiry);
            return "inquiryDetail";
        }
        return "redirect:/cs/inquiries";
    }


    @GetMapping("/inquiries")
    public String listUserInquiries(HttpSession httpSession,
                                    @RequestParam(value = "category", required = false) String category,
                                    Model model) {
        String authorId = (String) httpSession.getAttribute("username");

        List<Inquiry> inquiries;
        if (category != null && !category.isEmpty()) {
            inquiries = inquiryRepository.findAllByAuthorIdAndCategory(authorId, category);
        } else {
            inquiries = inquiryRepository.findAllByAuthorId(authorId);
        }

        model.addAttribute("inquiries", inquiries);
        return "mypageInquiryList";
    }





}

