import { TestBed } from '@angular/core/testing';

import { LightStateService } from './light-state.service';

describe('LightStateService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: LightStateService = TestBed.get(LightStateService);
    expect(service).toBeTruthy();
  });
});
