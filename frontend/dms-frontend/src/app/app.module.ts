import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DocumentListComponent } from './components/document-list/document-list.component';
import { DocumentDetailsComponent } from './components/document-details/document-details.component';
import { DocumentEditComponent } from './components/document-edit/document-edit.component';
import { DocumentUploadComponent } from './components/document-upload/document-upload.component';

@NgModule({
  declarations: [
    AppComponent,
    DocumentListComponent,
    DocumentDetailsComponent,
    DocumentEditComponent,
    DocumentUploadComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
