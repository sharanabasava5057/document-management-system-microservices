package com.example.metadata.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DocumentMetadataRequest {

    @NotBlank
    @Size(max = 255)
    private String title;

    @Size(max = 1000)
    private String description;

    @NotBlank
    @Size(max = 255)
    private String fileName;

    @PositiveOrZero
    private Long fileSize;

    @NotBlank
    @Size(max = 255)
    private String uploadedBy;
}
