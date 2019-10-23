import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ExtensionDialogComponent } from './extension-dialog.component';

describe('ExtensionDialogComponent', () => {
  let component: ExtensionDialogComponent;
  let fixture: ComponentFixture<ExtensionDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ExtensionDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExtensionDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
