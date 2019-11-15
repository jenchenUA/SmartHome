import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WarmFloorComponent } from './warm-floor.component';

describe('WarmFloorComponent', () => {
  let component: WarmFloorComponent;
  let fixture: ComponentFixture<WarmFloorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WarmFloorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WarmFloorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
