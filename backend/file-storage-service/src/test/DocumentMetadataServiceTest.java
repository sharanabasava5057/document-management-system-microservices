package com.example.metadata.service;

import com.example.metadata.dto.DocumentMetadataRequest;
import com.example.metadata.model.DocumentMetadata;
import com.example.metadata.repository.DocumentMetadataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentMetadataServiceTest {

    @Mock
    private DocumentMetadataRepository repository;

    @InjectMocks
    private DocumentMetadataService service;

    @Test
    void testCreate() {
        DocumentMetadataRequest req = new DocumentMetadataRequest();
        req.setTitle("Test File");
        req.setDescription("Desc");
        req.setFileName("a.pdf");
        req.setFileSize(100L);
        req.setUploadedBy("admin");

        when(repository.save(any(DocumentMetadata.class)))
                .thenAnswer(inv -> {
                    DocumentMetadata d = inv.getArgument(0);
                    d.setId(1L);
                    return d;
                });

        DocumentMetadata saved = service.create(req);

        assertThat(saved.getId()).isEqualTo(1L);
        assertThat(saved.getTitle()).isEqualTo("Test File");
        verify(repository, times(1)).save(any(DocumentMetadata.class));
    }

    @Test
    void testUpdate() {
        DocumentMetadata existing = new DocumentMetadata();
        existing.setId(1L);
        existing.setTitle("Old title");

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any(DocumentMetadata.class))).thenAnswer(inv -> inv.getArgument(0));

        DocumentMetadataRequest updateReq = new DocumentMetadataRequest();
        updateReq.setTitle("New Title");
        updateReq.setDescription("Updated");
        updateReq.setFileName("file.pdf");
        updateReq.setFileSize(200L);
        updateReq.setUploadedBy("user1");

        Optional<DocumentMetadata> updated = service.update(1L, updateReq);

        assertThat(updated).isPresent();
        assertThat(updated.get().getTitle()).isEqualTo("New Title");
    }

    @Test
    void testSearch() {
        when(repository.findAll(any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of()));

        service.search(null, 0, 10);

        verify(repository).findAll(any(PageRequest.class));
    }
}
