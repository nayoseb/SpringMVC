package com.nhnacademy.nhnmart.reposiyory;

import com.nhnacademy.nhnmart.domain.Inquiry;
import java.util.List;

public interface InquiryRepository {
    public Inquiry save(Inquiry inquiry);

    public Inquiry findById(Long id);

    public List<Inquiry> findAllByAuthorId(String authorId);

    public List<Inquiry> findAllUnanswered();
    public List<Inquiry> findAllByAuthorIdAndCategory(String authorId, String category);


}
