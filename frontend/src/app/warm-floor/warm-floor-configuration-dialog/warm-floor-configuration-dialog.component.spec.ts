import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WarmFloorConfigurationDialogComponent } from './warm-floor-configuration-dialog.component';

describe('WarmFloorConfigurationDialogComponent', () => {
  let component: WarmFloorConfigurationDialogComponent;
  let fixture: ComponentFixture<WarmFloorConfigurationDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WarmFloorConfigurationDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WarmFloorConfigurationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
