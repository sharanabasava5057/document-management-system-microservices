package com.example.files.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileStorageServiceImpl {

    private final Path storageDirectory = Paths.get("uploaded_files");

    public FileStorageServiceImpl() throws IOException {
        if (!Files.exists(storageDirectory)) {
            Files.createDirectories(storageDirectory);
        }
    }

    // ---------------------------------------------------------
    // SAVE CHUNK  (FIXED)
    // ---------------------------------------------------------
    public void saveChunk(String fileId, int chunkNumber, MultipartFile file) throws IOException {

        Path chunkPath = storageDirectory.resolve(fileId + "_chunk_" + chunkNumber);

        // FIX: allow overwriting existing chunks
        Files.write(chunkPath, file.getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
    }

    // ---------------------------------------------------------
    // FINALIZE UPLOAD
    // ---------------------------------------------------------
    public void finalizeUpload(String fileId, int totalChunks, String finalFileName) throws IOException {

        Path finalFile = storageDirectory.resolve(finalFileName);

        try {
            Files.deleteIfExists(finalFile);
            Files.createFile(finalFile);

            for (int i = 1; i <= totalChunks; i++) {
                Path chunk = storageDirectory.resolve(fileId + "_chunk_" + i);
                Files.write(finalFile, Files.readAllBytes(chunk), StandardOpenOption.APPEND);
                Files.delete(chunk);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error finalizing file.", e);
        }
    }

    // ---------------------------------------------------------
    // LIST FILES
    // ---------------------------------------------------------
    public List<String> listAllFiles() throws IOException {
        List<String> files = new ArrayList<>();

        DirectoryStream<Path> stream = Files.newDirectoryStream(storageDirectory);
        for (Path p : stream) {
            files.add(p.getFileName().toString());
        }

        return files;
    }

    // ---------------------------------------------------------
    // GET CHUNK
    // ---------------------------------------------------------
    public byte[] getChunk(String fileId, int chunkNumber, String fileName, int chunkSize) throws IOException {

        Path finalFile = storageDirectory.resolve(fileName);
        byte[] fileBytes = Files.readAllBytes(finalFile);

        int start = (chunkNumber - 1) * chunkSize;
        int end = Math.min(start + chunkSize, fileBytes.length);

        byte[] chunkData = new byte[end - start];
        System.arraycopy(fileBytes, start, chunkData, 0, chunkData.length);

        return chunkData;
    }

    // ---------------------------------------------------------
    // GET FULL FILE PATH
    // ---------------------------------------------------------
    public Path getFullFilePath(String fileName) {
        return storageDirectory.resolve(fileName);
    }
}
