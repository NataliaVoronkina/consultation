package com.heliosx.consultation.infrastructure;

import com.heliosx.consultation.domain.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    Optional<Consultation> findByConsultationUid(UUID consultationUid);

}
