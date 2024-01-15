package com.nhnacademy.nhnmart.config;


import com.nhnacademy.nhnmart.Base;
import com.nhnacademy.nhnmart.domain.Answer;
import com.nhnacademy.nhnmart.domain.Inquiry;
import com.nhnacademy.nhnmart.domain.Role;
import com.nhnacademy.nhnmart.reposiyory.AnswerRepository;
import com.nhnacademy.nhnmart.reposiyory.AnswerRepositoryImpl;
import com.nhnacademy.nhnmart.reposiyory.InquiryRepository;
import com.nhnacademy.nhnmart.reposiyory.UserRepository;
import java.time.LocalDateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(basePackageClasses = Base.class,
        excludeFilters = {@ComponentScan.Filter(Controller.class)})
public class RootConfig {


    private final InquiryRepository inquiryRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;

    public RootConfig(InquiryRepository inquiryRepository, AnswerRepository answerRepository,
                      UserRepository userRepository) {
        this.inquiryRepository = inquiryRepository;
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
    }

    @Bean
    public UserRepository userRepository() {
        userRepository.addUser("admin", "12345", Role.ADMIN, "관리자");
        userRepository.addUser("user", "12345", Role.USER, "김인직");

        return userRepository;
    }


    @Bean
    public InquiryRepository inquiryRepository() {

        // 예시: Inquiry 객체 생성 및 추가
        Answer answer1 = Answer.builder()
                .inquiryId(1L)
                .content("제품 결함에 대해 죄송합니다. 교환해드리겠습니다.")
                .responseDate(LocalDateTime.now())
                .csRepresentativeId("admin")
                .build();

        Answer answer2 = Answer.builder()
                .inquiryId(2L)
                .content("환불 절차를 안내해 드리겠습니다.")
                .responseDate(LocalDateTime.now())
                .csRepresentativeId("admin")
                .build();
        Inquiry inquiry1 = Inquiry.builder()
                .title("제품 결함 문의")
                .category("불만 접수")
                .content("제품에 결함이 있어요.")
                .creationDate(LocalDateTime.now())
                .authorId("user")
                .attachments(null)
                .isAnswered(true)
                .answer(answer1)
                .build();

        Inquiry inquiry2 = Inquiry.builder()
                .title("환불 요청")
                .category("환불/교환")
                .content("환불을 원합니다.")
                .creationDate(LocalDateTime.now())
                .authorId("user")
                .attachments(null)
                .isAnswered(true)
                .answer(answer2)
                .build();

        Inquiry inquiry3 = Inquiry.builder()
                .title("배송 문의")
                .category("기타 문의")
                .content("배송이 언제 되나요?")
                .creationDate(LocalDateTime.now())
                .authorId("user3")
                .attachments(null)
                .isAnswered(false)
                .answer(null)
                .build();

        Inquiry inquiry4 = Inquiry.builder()
                .title("제품 사용법 문의")
                .category("제안")
                .content("이 제품은 어떻게 사용하나요?")
                .creationDate(LocalDateTime.now())
                .authorId("user4")
                .attachments(null)
                .isAnswered(false)
                .answer(null)
                .build();
        inquiryRepository.save(inquiry1);
        inquiryRepository.save(inquiry2);
        inquiryRepository.save(inquiry3);
        inquiryRepository.save(inquiry4);

        // 예시: Answer 객체 생성 및 추가
        answerRepository.save(answer1);
        answerRepository.save(answer2);
        return inquiryRepository;
    }





}
