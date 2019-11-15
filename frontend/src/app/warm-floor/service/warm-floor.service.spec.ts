import { TestBed } from '@angular/core/testing';

import { WarmFloorService } from './warm-floor.service';

describe('WarmFloorService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: WarmFloorService = TestBed.get(WarmFloorService);
    expect(service).toBeTruthy();
  });
});
