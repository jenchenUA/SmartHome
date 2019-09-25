import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RemoveConfirmationDialogComponent } from './remove-confirmation.dialog.component';

describe('RemoveConfirmation.DialogComponent', () => {
  let component: RemoveConfirmationDialogComponent;
  let fixture: ComponentFixture<RemoveConfirmationDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RemoveConfirmationDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RemoveConfirmationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
