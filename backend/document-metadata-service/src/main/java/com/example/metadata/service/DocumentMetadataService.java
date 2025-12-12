package com.example.metadata.service;

import com.example.metadata.model.DocumentMetadata;
import com.example.metadata.repository.DocumentMetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class DocumentMetadataService {

    @Autowired
    private DocumentMetadataRepository repository;

    public DocumentMetadata create(DocumentMetadata doc) {
        if (doc.getUploadDate() == null) {
            doc.setUploadDate(Instant.now());
        }
        return repository.save(doc);
    }

    public Optional<DocumentMetadata> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<DocumentMetadata> update(Long id, DocumentMetadata updated) {
        return repository.findById(id).map(existing -> {
            existing.setTitle(updated.getTitle());
            existing.setDescription(updated.getDescription());
            existing.setFileName(updated.getFileName());
            existing.setFileSize(updated.getFileSize());
            existing.setUploadedBy(updated.getUploadedBy());

            if (updated.getUploadDate() != null) {
                existing.setUploadDate(updated.getUploadDate());
            }

            return repository.save(existing);
        });
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<DocumentMetadata> search(
            String title,
            String description,
            String uploadedBy,
            Instant from,
            Instant to,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("uploadDate").descending());
        return repository.search(title, description, uploadedBy, from, to, pageable);
    }
}
