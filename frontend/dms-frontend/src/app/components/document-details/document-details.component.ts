import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DocumentService } from '../../services/document.service';

@Component({
  selector: 'app-document-details',
  templateUrl: './document-details.component.html',
  styleUrls: ['./document-details.component.css']
})
export class DocumentDetailsComponent implements OnInit {
  doc: any = null;
  loading = false;
  error = '';

  constructor(
    private route: ActivatedRoute,
    private docService: DocumentService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (!id) {
      this.error = 'Invalid document id';
      return;
    }
    this.loading = true;
    this.docService.getById(id).subscribe({
      next: data => {
        this.doc = data;
        this.loading = false;
      },
      error: err => {
        console.error(err);
        this.error = 'Failed to load document';
        this.loading = false;
      }
    });
  }

  download() {
    if (!this.doc || !this.doc.fileName) return;
    const url = this.docService.getDownloadUrl(this.doc.fileName);
    window.open(url, '_blank');
  }

  edit() {
    this.router.navigate(['/documents', this.doc.id, 'edit']);
  }
}
