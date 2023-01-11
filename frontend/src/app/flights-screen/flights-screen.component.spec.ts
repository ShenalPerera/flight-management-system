import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FlightsScreenComponent } from './flights-screen.component';

describe('FlightsComponent', () => {
  let component: FlightsScreenComponent;
  let fixture: ComponentFixture<FlightsScreenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FlightsScreenComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FlightsScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
