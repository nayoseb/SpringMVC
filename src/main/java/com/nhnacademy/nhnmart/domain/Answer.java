package com.nhnacademy.nhnmart.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Answer {
    private Long id;
    private Long inquiryId; // 연결된 문의의 ID
    private String content; // 답변 내용
    private LocalDateTime responseDate; // 답변 작성일시
    private String csRepresentativeId; // 답변한 CS담당자의 ID

    @Builder
    public Answer(Long id, Long inquiryId, String content, LocalDateTime responseDate, String csRepresentativeId) {
        this.id = id;
        this.inquiryId = inquiryId;
        this.content = content;
        this.responseDate = responseDate;
        this.csRepresentativeId = csRepresentativeId;
    }

}

