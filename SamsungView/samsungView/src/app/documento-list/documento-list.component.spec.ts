import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentoListComponent } from './documento-list.component';

describe('DocumentoListComponent', () => {
  let component: DocumentoListComponent;
  let fixture: ComponentFixture<DocumentoListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DocumentoListComponent]
    });
    fixture = TestBed.createComponent(DocumentoListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
