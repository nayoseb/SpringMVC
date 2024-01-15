package com.nhnacademy.nhnmart.reposiyory;

import com.nhnacademy.nhnmart.domain.Answer;

public interface AnswerRepository {

    public Answer save(Answer answer);

    public Answer findById(Long id);
}
