package com.nhnacademy.nhnmart.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.nhnmart.domain.Inquiry;
import com.nhnacademy.nhnmart.reposiyory.AnswerRepositoryImpl;
import com.nhnacademy.nhnmart.reposiyory.InquiryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AnswerRepositoryImpl answerRepositoryImpl;

    @Mock
    private InquiryRepository inquiryRepository;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    void testShowAnswerForm() throws Exception {
        Long inquiryId = 1L;
        Inquiry mockInquiry = new Inquiry();
        mockInquiry.setId(inquiryId);
        when(inquiryRepository.findById(inquiryId)).thenReturn(mockInquiry);

        mockMvc.perform(get("/cs/admin/answers/new")
                        .param("inquiryId", inquiryId.toString()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("answerRegisterDto", "inquiry"))
                .andExpect(view().name("answerForm"));
    }

    @Test
    void testRegisterAnswer() throws Exception {


        mockMvc.perform(post("/cs/admin/answers")
                        .param("comment", "Test Answer")
                        .param("inquiryId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/admin/unanswered-inquiries"));


    }

    @Test
    void testShowUnansweredInquiries() throws Exception {
        mockMvc.perform(get("/cs/admin/unanswered-inquiries"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("inquiries"))
                .andExpect(view().name("inquiryList"));
    }
}
