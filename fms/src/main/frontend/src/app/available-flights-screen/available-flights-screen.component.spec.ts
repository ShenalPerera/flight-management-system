import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AvailableFlightsScreenComponent } from './available-flights-screen.component';

describe('AvailableFlightsScreenComponent', () => {
  let component: AvailableFlightsScreenComponent;
  let fixture: ComponentFixture<AvailableFlightsScreenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AvailableFlightsScreenComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AvailableFlightsScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
