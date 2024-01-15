package com.nhnacademy.nhnmart.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.nhnmart.domain.Inquiry;
import com.nhnacademy.nhnmart.reposiyory.InquiryRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@ExtendWith(MockitoExtension.class)
class InquiryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private InquiryRepository inquiryRepository;

    @InjectMocks
    private InquiryController inquiryController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(inquiryController).build();
    }

    @Test
    void testShowInquiryForm() throws Exception {
        mockMvc.perform(get("/cs/inquiries/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("inquiryRegisterDto"))
                .andExpect(view().name("inquiryForm"));
    }


    @Test
    void testShowInquiryDetail() throws Exception {
        Long inquiryId = 1L;
        Inquiry mockInquiry = new Inquiry();
        mockInquiry.setId(inquiryId);
        mockInquiry.setTitle("Test Inquiry");
        mockInquiry.setCategory("General");
        mockInquiry.setContent("Test Content");
        mockInquiry.setCreationDate(LocalDateTime.now());
        mockInquiry.setAuthorId("user");
        mockInquiry.setAttachments(Collections.emptyList());
        mockInquiry.setAnswered(false);

        when(inquiryRepository.findById(inquiryId)).thenReturn(mockInquiry);

        mockMvc.perform(get("/cs/inquiry/{id}", inquiryId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("inquiry"))
                .andExpect(view().name("inquiryDetail"));
    }

    @Test
    void testListUserInquiries() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("username", "user");

        mockMvc.perform(get("/cs/inquiries")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("inquiries"))
                .andExpect(view().name("mypageInquiryList"));
    }

}
