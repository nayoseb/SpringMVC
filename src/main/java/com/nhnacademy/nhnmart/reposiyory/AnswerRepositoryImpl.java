package com.nhnacademy.nhnmart.reposiyory;

import com.nhnacademy.nhnmart.domain.Answer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class AnswerRepositoryImpl implements AnswerRepository{
    private final Map<Long, Answer> answerMap = new ConcurrentHashMap<>();
    private long currentId = 1;

    public Answer save(Answer answer) {
        if (answer.getId() == null) {
            answer.setId(currentId++);
        }
        answerMap.put(answer.getId(), answer);
        return answer;
    }

    public Answer findById(Long id) {
        return answerMap.get(id);
    }

    // 필요하다면, 추가적인 메소드 구현
}
