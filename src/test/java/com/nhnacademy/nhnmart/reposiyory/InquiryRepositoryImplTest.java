package com.nhnacademy.nhnmart.reposiyory;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.nhnmart.domain.Inquiry;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InquiryRepositoryImplTest {

    private InquiryRepositoryImpl inquiryRepository;

    @BeforeEach
    void setUp() {
        inquiryRepository = new InquiryRepositoryImpl();
        // 답변된 문의 추가
        inquiryRepository.save(
                new Inquiry(1L, "Answered Inquiry 1", "Category 1", "Content 1", LocalDateTime.now(), "user1", null,
                        true, null));
        inquiryRepository.save(
                new Inquiry(2L, "Answered Inquiry 2", "Category 2", "Content 2", LocalDateTime.now(), "user2", null,
                        true, null));

        // 답변되지 않은 문의 추가
        inquiryRepository.save(
                new Inquiry(3L, "Unanswered Inquiry 1", "Category 3", "Content 3", LocalDateTime.now(), "user3", null,
                        false, null));
        inquiryRepository.save(
                new Inquiry(4L, "Unanswered Inquiry 2", "Category 4", "Content 4", LocalDateTime.now(), "user4", null,
                        false, null));
    }

    @Test
    void testSaveAndFindById() {
        Inquiry inquiry = Inquiry.builder()
                .title("Test Inquiry")
                .category("Test Category")
                .content("Test Content")
                .creationDate(LocalDateTime.now())
                .authorId("user1")
                .build();

        Inquiry savedInquiry = inquiryRepository.save(inquiry);

        // 저장된 Inquiry 객체의 ID로 조회
        Inquiry foundInquiry = inquiryRepository.findById(savedInquiry.getId());

        assertThat(foundInquiry).isNotNull();
        assertThat(foundInquiry.getTitle()).isEqualTo("Test Inquiry");
        assertThat(foundInquiry.getAuthorId()).isEqualTo("user1");
    }

    @Test
    void testFindAllByAuthorId() {


        List<Inquiry> inquiries = inquiryRepository.findAllByAuthorId("user1");

        assertThat(inquiries).hasSize(1);
        assertThat(inquiries.get(0).getAuthorId()).isEqualTo("user1");
    }

    @Test
    void testFindAllByAuthorIdAndCategory() {
        List<Inquiry> inquiries = inquiryRepository.findAllByAuthorIdAndCategory("user1", "Category 1");

        assertThat(inquiries).hasSize(1);
        assertThat(inquiries.get(0).getCategory()).isEqualTo("Category 1");
    }

    @Test
    void testFindAllUnanswered() {
        List<Inquiry> unansweredInquiries = inquiryRepository.findAllUnanswered();

        assertThat(unansweredInquiries).hasSize(2);
        assertThat(unansweredInquiries).allMatch(inquiry -> !inquiry.isAnswered());
    }
}
