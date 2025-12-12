import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class FileUploadService {

  private BASE_URL = `${environment.apiUrl}/file-storage/api/files`;

  constructor(private http: HttpClient) {}

  uploadChunk(fileId: string, chunkNumber: number, chunk: Blob, fileName: string) {
    const formData = new FormData();
    formData.append('file', chunk, fileName);
    formData.append('fileId', fileId);
    formData.append('chunkNumber', chunkNumber.toString());

    return this.http.post(`${this.BASE_URL}/upload-chunk`, formData);
  }

  finalizeUpload(fileId: string, totalChunks: number, fileName: string) {
    return this.http.post(`${this.BASE_URL}/finalize-upload`, {
      fileId, totalChunks, fileName
    });
  }
}
