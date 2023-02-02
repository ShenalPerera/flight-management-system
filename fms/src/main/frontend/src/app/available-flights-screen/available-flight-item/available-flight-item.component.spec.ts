import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AvailableFlightItemComponent } from './available-flight-item.component';

describe('AvailableFlightItemComponent', () => {
  let component: AvailableFlightItemComponent;
  let fixture: ComponentFixture<AvailableFlightItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AvailableFlightItemComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AvailableFlightItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
