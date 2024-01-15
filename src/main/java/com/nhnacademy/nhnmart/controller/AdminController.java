package com.nhnacademy.nhnmart.controller;

import com.nhnacademy.nhnmart.domain.Answer;
import com.nhnacademy.nhnmart.domain.Inquiry;
import com.nhnacademy.nhnmart.dto.AnswerRegisterDto;
import com.nhnacademy.nhnmart.reposiyory.AnswerRepositoryImpl;
import com.nhnacademy.nhnmart.reposiyory.InquiryRepository;
import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cs/admin")
public class AdminController {
    private final AnswerRepositoryImpl answerRepositoryImpl;
    private final InquiryRepository inquiryRepository;

    public AdminController(AnswerRepositoryImpl answerRepositoryImpl, InquiryRepository inquiryRepository) {
        this.answerRepositoryImpl = answerRepositoryImpl;
        this.inquiryRepository = inquiryRepository;
    }

    @GetMapping("/answers/new")
    public String showAnswerForm(@RequestParam("inquiryId") Long inquiryId, Model model) {
        AnswerRegisterDto answerRegisterDto = new AnswerRegisterDto();
        answerRegisterDto.setInquiryId(inquiryId);

        model.addAttribute("answerRegisterDto", answerRegisterDto);
        model.addAttribute("inquiry", inquiryRepository.findById(inquiryId));
        return "answerForm";
    }


    @PostMapping("/answers")
    public String registerAnswer(@Valid AnswerRegisterDto answerRegisterDto,
                                 BindingResult bindingResult,
                                 HttpSession session,
                                 Model model) {
        Long inquiryId = answerRegisterDto.getInquiryId();
        if (bindingResult.hasErrors()) {
            model.addAttribute("inquiry", inquiryRepository.findById(inquiryId));
            return "answerForm"; // 유효성 검사 실패 시, 폼으로 돌아감
        }

        String csRepresentativeId = (String) session.getAttribute("username");
        Answer answer = Answer.builder()
                .inquiryId(inquiryId)
                .content(answerRegisterDto.getComment())
                .responseDate(LocalDateTime.now())
                .csRepresentativeId(csRepresentativeId)
                .build();

        answerRepositoryImpl.save(answer);

        Inquiry inquiry = inquiryRepository.findById(inquiryId);
        if (inquiry != null) {
            inquiry.setAnswered(true);
            inquiry.setAnswer(answer);
            inquiryRepository.save(inquiry);
        }

        return "redirect:/cs/admin/unanswered-inquiries";
    }



    @GetMapping("/unanswered-inquiries")
    public String showUnansweredInquiries(Model model) {
        List<Inquiry> unansweredInquiries = inquiryRepository.findAllUnanswered();
        model.addAttribute("inquiries",unansweredInquiries);
        return "inquiryList";
    }

}

