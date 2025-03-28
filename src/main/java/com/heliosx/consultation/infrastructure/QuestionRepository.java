package com.heliosx.consultation.infrastructure;

import com.heliosx.consultation.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAll();

    @Override
    Optional<Question> findById(Long id);

    @Query(value = """
            SELECT q.* 
            FROM question q
            JOIN consultation c ON q.consultation_id = c.id
            WHERE c.consultation_uid = :consultationUid
            """, nativeQuery = true)
    List<Question> findQuestionsByConsultationUid(UUID consultationUid);
}
