import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LightConfigurationComponent } from './light-configuration.component';

describe('LightConfigurationComponent', () => {
  let component: LightConfigurationComponent;
  let fixture: ComponentFixture<LightConfigurationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LightConfigurationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LightConfigurationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
