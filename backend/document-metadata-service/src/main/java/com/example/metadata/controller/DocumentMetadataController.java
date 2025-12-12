package com.example.metadata.controller;

import com.example.metadata.model.DocumentMetadata;
import com.example.metadata.service.DocumentMetadataService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/api/documents")
public class DocumentMetadataController {

    @Autowired
    private DocumentMetadataService service;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody DocumentMetadata dto) {
        DocumentMetadata saved = service.create(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody DocumentMetadata updated) {
        return service.update(id, updated)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String uploadedBy,
            @RequestParam(required = false) String uploadDateFrom,
            @RequestParam(required = false) String uploadDateTo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Instant from = null, to = null;
        try {
            if (uploadDateFrom != null) from = Instant.parse(uploadDateFrom);
            if (uploadDateTo != null) to = Instant.parse(uploadDateTo);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Invalid date format (use ISO-8601)");
        }

        Page<DocumentMetadata> result = service.search(title, description, uploadedBy, from, to, page, size);
        return ResponseEntity.ok(result);
    }
}
