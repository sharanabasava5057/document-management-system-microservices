import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FileUploadService } from '../../services/file-upload.service';
import { DocumentService } from '../../services/document.service';

@Component({
  selector: 'app-document-upload',
  templateUrl: './document-upload.component.html',
  styleUrls: ['./document-upload.component.css']
})
export class DocumentUploadComponent {

  title = '';
  description = '';
  uploadedBy = '';
  selectedFile: File | null = null;

  progress = 0;
  uploading = false;
  message = '';
  error = '';

  readonly CHUNK_SIZE = 1024 * 1024; // 1MB chunks

  constructor(
    private uploadService: FileUploadService,
    private documentService: DocumentService,
    private router: Router
  ) {}

  // When user selects a file
  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0] || null;
    this.progress = 0;
    this.message = '';
    this.error = '';
  }

  // Upload document
  async upload() {
    if (!this.selectedFile) {
      this.error = 'Please select a file';
      return;
    }
    if (!this.title.trim()) {
      this.error = 'Title is required';
      return;
    }
    if (!this.uploadedBy.trim()) {
      this.error = 'Uploaded By is required';
      return;
    }

    const file = this.selectedFile;
    const fileId = Date.now().toString();   // Unique file ID
    const totalChunks = Math.ceil(file.size / this.CHUNK_SIZE);

    this.uploading = true;

    try {
      // 1️⃣ Upload chunks one by one
      for (let chunkNumber = 1; chunkNumber <= totalChunks; chunkNumber++) {

        const start = (chunkNumber - 1) * this.CHUNK_SIZE;
        const end = Math.min(start + this.CHUNK_SIZE, file.size);
        const chunk = file.slice(start, end);

        await this.uploadService.uploadChunk(fileId, chunkNumber, chunk, file.name).toPromise();

        this.progress = Math.round((end / file.size) * 100);
      }

      // 2️⃣ Finalize the upload
      await this.uploadService.finalizeUpload(fileId, totalChunks, file.name).toPromise();

      // 3️⃣ Save document metadata in DB
      await this.documentService.create({
        title: this.title,
        description: this.description,
        uploadedBy: this.uploadedBy,
        fileName: file.name,
        fileSize: file.size
      }).toPromise();

      this.message = 'Upload successful!';
      this.uploading = false;

      // Optional: Redirect to list page
      setTimeout(() => this.router.navigate(['/documents']), 1000);

    } catch (err) {
      console.error(err);
      this.error = 'Upload failed.';
      this.uploading = false;
    }
  }
}
