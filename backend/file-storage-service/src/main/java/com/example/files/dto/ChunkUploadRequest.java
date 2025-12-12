package com.example.files.dto;

import lombok.Data;

@Data
public class ChunkUploadRequest {
    private String fileId;
    private Integer chunkNumber;
    private Integer totalChunks;
}
