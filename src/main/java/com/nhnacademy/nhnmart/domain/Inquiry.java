package com.nhnacademy.nhnmart.domain;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Inquiry {
    private Long id;
    private String title;
    private String category;
    private String content;
    private LocalDateTime creationDate;
    private String authorId;
    private List<String> attachments;
    private boolean isAnswered;
    private Answer answer;



    @Builder
    public Inquiry(Long id, String title, String category, String content, LocalDateTime creationDate, String authorId,
                   List<String> attachments, boolean isAnswered, Answer answer) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.content = content;
        this.creationDate = creationDate;
        this.authorId = authorId;
        this.attachments = attachments;
        this.isAnswered = isAnswered;
        this.answer = answer;
    }
}

