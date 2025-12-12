package com.example.files.dto;

import lombok.Data;

@Data
public class FinalizeUploadRequest {
    private String fileId;
    private Integer totalChunks;
    private String fileName;
}
