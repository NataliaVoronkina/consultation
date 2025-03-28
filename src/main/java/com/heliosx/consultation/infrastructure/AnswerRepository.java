package com.heliosx.consultation.infrastructure;

import com.heliosx.consultation.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query(value = """
            SELECT consultation_uid, a.* FROM answer a
            JOIN consultation c ON a.consultation_id = c.id
            WHERE c.consultation_uid = :consultationUid
            """, nativeQuery = true)
    List<Answer> findAnswersByConsultationUid(UUID consultationUid);

}
