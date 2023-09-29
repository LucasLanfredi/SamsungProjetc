import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';

import { DataTablesModule } from 'angular-datatables'; // Importe DataTablesModule
import { NgxDatatableModule } from '@swimlane/ngx-datatable';

import { AppComponent } from './app.component';
import { DocumentoListComponent } from './documento-list/documento-list.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

const routes = [
  { path: 'documento-list', component: DocumentoListComponent },
];

@NgModule({
  declarations: [
    AppComponent,
    DocumentoListComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(routes),
    DataTablesModule.forRoot(), // Importe DataTablesModule.forRoot()
    NgxDatatableModule, BrowserAnimationsModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
