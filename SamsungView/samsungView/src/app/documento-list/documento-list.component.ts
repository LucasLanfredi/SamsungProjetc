import { Component, OnInit, AfterViewInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { DataTableDirective } from 'angular-datatables';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-documento-list',
  templateUrl: './documento-list.component.html',
  styleUrls: ['./documento-list.component.css'],
})
export class DocumentoListComponent implements OnInit, AfterViewInit {
  documentNumberFilter: string | null = null;
  startDateFilter: string | null = null;
  endDateFilter: string | null = null;
  currencyCodeFilter: string | null = null;
  currencyOptions: string[] = [];
  documents: any[] = [];

  dtOptions: DataTables.Settings = {};
  dtTrigger: Subject<any> = new Subject();

  selectedCurrencyCodes: string[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.http.get<string[]>('http://localhost:8080/produtos/moedas').subscribe((data: string[]) => {
      this.currencyOptions = data;
    });

    this.http.get<any[]>('http://localhost:8080/produtos/todosDocumentos').subscribe((data: any[]) => {
      this.documents = data;
      this.dtTrigger.next(null); 
    });
  }

  ngAfterViewInit(): void {
    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 10,
    };
  }

  filterDocuments(): void {
    const filter = {
      documentNumberFilter: this.documentNumberFilter || null,
      startDateFilter: this.startDateFilter || null,
      endDateFilter: this.endDateFilter || null,
      currencyCodeFilter: this.selectedCurrencyCodes || null,
    };

    this.http.post<any>('http://localhost:8080/produtos/buscarDocumentoComFiltro', filter).subscribe((data: any[]) => {
      this.documents = data;
      this.dtTrigger.next(null); 
    });
  }

  // Método para limpar os filtros
  clearFilters(): void {
    this.documentNumberFilter = null;
    this.startDateFilter = null;
    this.endDateFilter = null;
    this.ngOnInit();
  }
}
