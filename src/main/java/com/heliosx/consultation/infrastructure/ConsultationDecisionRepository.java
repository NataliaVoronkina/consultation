package com.heliosx.consultation.infrastructure;

import com.heliosx.consultation.domain.ConsultationDecision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConsultationDecisionRepository extends JpaRepository<ConsultationDecision, UUID> {

    ConsultationDecision save(ConsultationDecision consultationDecision);

    @Query(value = """
            SELECT cd.* 
            FROM consultation_decision cd
            JOIN consultation c ON cd.consultation_id = c.id
            WHERE c.consultation_uid = :consultationUid
            """, nativeQuery = true)
    Optional<ConsultationDecision> findConsultationDecision(UUID consultationUid);
}
