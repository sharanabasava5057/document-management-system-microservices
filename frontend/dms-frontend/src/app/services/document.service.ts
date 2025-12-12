import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DocumentService {
  // Gateway routes: metadata service is mounted at /metadata
  private baseUrl = `${environment.apiUrl}/metadata/api/documents`;

  constructor(private http: HttpClient) {}

  // List / search (returns server page object)
  list(query: string = ''): Observable<any> {
    const q = encodeURIComponent(query || '');
    return this.http.get(`${this.baseUrl}/search?title=${q}`);
  }

  getById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  create(doc: any): Observable<any> {
    return this.http.post(this.baseUrl, doc);
  }

  update(id: number, doc: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/${id}`, doc);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }

  // Helper to build download URL (frontend will open or fetch it)
  getDownloadUrl(fileName: string): string {
    return `${environment.apiUrl}/file-storage/api/files/download/${encodeURIComponent(fileName)}`;
  }
}
