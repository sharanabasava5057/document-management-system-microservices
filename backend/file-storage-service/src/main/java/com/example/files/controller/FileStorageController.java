package com.example.files.controller;

import com.example.files.dto.ApiResponse;
import com.example.files.dto.FinalizeUploadRequest;
import com.example.files.service.FileStorageServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@Tag(name = "File Storage", description = "Chunk upload, merge, and download")
public class FileStorageController {

    @Autowired
    private FileStorageServiceImpl storageService;

    // upload chunk
    @PostMapping("/upload-chunk")
    public ResponseEntity<ApiResponse<Void>> uploadChunk(
            @RequestParam("file") MultipartFile file,
            @RequestParam("fileId") String fileId,
            @RequestParam("chunkNumber") Integer chunkNumber) throws Exception {

        storageService.saveChunk(fileId, chunkNumber, file);

        return ResponseEntity.ok(ApiResponse.ok(null, "Chunk uploaded"));
    }

    // finalize upload
    @PostMapping("/finalize-upload")
    public ResponseEntity<ApiResponse<Void>> finalizeUpload(
            @RequestBody FinalizeUploadRequest request) throws Exception {

        storageService.finalizeUpload(
                request.getFileId(),
                request.getTotalChunks(),
                request.getFileName()
        );

        return ResponseEntity.ok(ApiResponse.ok(null, "File saved successfully"));
    }

    // list files
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<String>>> listFiles() throws Exception {
        return ResponseEntity.ok(ApiResponse.ok(storageService.listAllFiles(), "OK"));
    }

    // full file download
    @GetMapping(value = "/download/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> downloadFull(@PathVariable String fileName) throws Exception {

        Path path = storageService.getFullFilePath(fileName);

        if (!Files.exists(path)) {
            return ResponseEntity.notFound().build();
        }

        byte[] data = Files.readAllBytes(path);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(data);
    }
}
