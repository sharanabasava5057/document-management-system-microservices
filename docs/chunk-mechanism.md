# Chunk Upload/Download Mechanism

## Upload

1. Angular reads selected file, splits into 1MB chunks.
2. For each chunk:
   - POST `/api/files/upload-chunk`
   - Params: fileId, chunkNumber, totalChunks
   - Body: `MultipartFile file`
3. After last chunk:
   - POST `/api/files/finalize-upload`
   - Params: fileId, totalChunks, fileName
4. FileStorageService:
   - Saves each chunk as `storage/{fileId}/chunk-{n}`
   - On finalize, concatenates all chunk files into final file.

## Download

1. Angular calls
   - GET `/api/files/download-chunk/{fileId}/{chunkNumber}?fileName=...&chunkSize=...`
2. Service reads a portion of final file (based on chunkNumber + chunkSize).
3. Returns `application/octet-stream` bytes.

Client can assemble chunks or stream to disk.
