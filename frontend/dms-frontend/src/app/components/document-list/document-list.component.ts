import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DocumentService } from '../../services/document.service';

@Component({
  selector: 'app-document-list',
  templateUrl: './document-list.component.html',
  styleUrls: ['./document-list.component.css']
})
export class DocumentListComponent implements OnInit {
  documents: any[] = [];
  pageInfo: any = {};
  query = '';
  loading = false;
  error = '';

  constructor(private docService: DocumentService, private router: Router) {}

  ngOnInit(): void {
    this.load();
  }

  load() {
    this.loading = true;
    this.docService.list(this.query).subscribe({
      next: (res: any) => {
        // server returns a Page<DocumentMetadata>
        if (res && res.content) {
          this.documents = res.content;
          this.pageInfo = {
            totalElements: res.totalElements,
            totalPages: res.totalPages,
            number: res.number
          };
        } else if (Array.isArray(res)) {
          // fallback
          this.documents = res;
        } else {
          this.documents = [];
        }
        this.loading = false;
      },
      error: err => {
        console.error(err);
        this.error = 'Failed to load documents';
        this.loading = false;
      }
    });
  }

  view(doc: any) {
    this.router.navigate(['/documents', doc.id]);
  }

  edit(doc: any) {
    this.router.navigate(['/documents', doc.id, 'edit']);
  }

  delete(doc: any) {
    if (!confirm(`Delete "${doc.title}"?`)) return;
    this.docService.delete(doc.id).subscribe({
      next: () => this.load(),
      error: err => {
        console.error(err);
        alert('Delete failed');
      }
    });
  }

  download(doc: any) {
    const url = this.docService.getDownloadUrl(doc.fileName);
    // open in new tab to trigger browser download
    window.open(url, '_blank');
  }
}
