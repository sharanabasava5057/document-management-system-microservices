package com.example.metadata.repository;

import com.example.metadata.model.DocumentMetadata;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.Instant;

public interface DocumentMetadataRepository extends JpaRepository<DocumentMetadata, Long> {

    @Query("""
      SELECT d FROM DocumentMetadata d
      WHERE (:title IS NULL OR LOWER(d.title) LIKE LOWER(CONCAT('%',:title,'%')))
        AND (:description IS NULL OR LOWER(d.description) LIKE LOWER(CONCAT('%',:description,'%')))
        AND (:uploadedBy IS NULL OR LOWER(d.uploadedBy) = LOWER(:uploadedBy))
        AND (:fromDate IS NULL OR d.uploadDate >= :fromDate)
        AND (:toDate IS NULL OR d.uploadDate <= :toDate)
    """)
    Page<DocumentMetadata> search(
            @Param("title") String title,
            @Param("description") String description,
            @Param("uploadedBy") String uploadedBy,
            @Param("fromDate") Instant fromDate,
            @Param("toDate") Instant toDate,
            Pageable pageable
    );
}
