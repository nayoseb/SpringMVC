package com.nhnacademy.nhnmart.reposiyory;

import com.nhnacademy.nhnmart.domain.Inquiry;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class InquiryRepositoryImpl implements InquiryRepository{
    private final Map<Long, Inquiry> inquiryMap = new ConcurrentHashMap<>();
    private long currentId = 1;

    public Inquiry save(Inquiry inquiry) {
        if (inquiry.getId() == null) {
            inquiry.setId(currentId++);
        }
        inquiryMap.put(inquiry.getId(), inquiry);
        return inquiry;
    }

    public Inquiry findById(Long id) {
        return inquiryMap.get(id);
    }

    public List<Inquiry> findAllByAuthorId(String authorId) {
        return inquiryMap.values().stream()
                .filter(inquiry -> inquiry.getAuthorId().equals(authorId))
                .sorted(Comparator.comparing(Inquiry::getCreationDate).reversed())
                .collect(Collectors.toList());
    }

    public List<Inquiry> findAllByAuthorIdAndCategory(String authorId, String category) {
        return inquiryMap.values().stream()
                .filter(inquiry -> inquiry.getAuthorId().equals(authorId) && inquiry.getCategory().equals(category))
                .sorted(Comparator.comparing(Inquiry::getCreationDate).reversed())
                .collect(Collectors.toList());
    }


    public List<Inquiry> findAllUnanswered() {
        return inquiryMap.values().stream()
                .filter(inquiry -> !inquiry.isAnswered())
                .sorted(Comparator.comparing(Inquiry::getCreationDate).reversed())
                .collect(Collectors.toList());
    }
}
