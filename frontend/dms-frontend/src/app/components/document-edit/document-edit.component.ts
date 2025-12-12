import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DocumentService } from '../../services/document.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-document-edit',
  templateUrl: './document-edit.component.html',
  styleUrls: ['./document-edit.component.css']
})
export class DocumentEditComponent implements OnInit {
  doc: any = { title: '', description: '', uploadedBy: '', fileName: '' };
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
      this.error = 'Invalid id';
      return;
    }
    this.loading = true;
    this.docService.getById(id).subscribe({
      next: d => {
        this.doc = d;
        this.loading = false;
      },
      error: err => {
        console.error(err);
        this.error = 'Failed to load';
        this.loading = false;
      }
    });
  }

  save(form: NgForm) {
    if (!form.valid) return;
    this.docService.update(this.doc.id, this.doc).subscribe({
      next: () => this.router.navigate(['/documents', this.doc.id]),
      error: err => {
        console.error(err);
        alert('Save failed');
      }
    });
  }
}
