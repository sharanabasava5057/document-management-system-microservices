import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DocumentListComponent } from './components/document-list/document-list.component';
import { DocumentDetailsComponent } from './components/document-details/document-details.component';
import { DocumentEditComponent } from './components/document-edit/document-edit.component';
import { DocumentUploadComponent } from './components/document-upload/document-upload.component'; // you have

const routes: Routes = [
  { path: '', redirectTo: 'documents', pathMatch: 'full' },
  { path: 'documents', component: DocumentListComponent },
  { path: 'documents/:id', component: DocumentDetailsComponent },
  { path: 'documents/:id/edit', component: DocumentEditComponent },
  { path: 'upload', component: DocumentUploadComponent },
  // add other routes as required
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
