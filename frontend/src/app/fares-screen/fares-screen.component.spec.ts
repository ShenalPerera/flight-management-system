import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FaresScreenComponent } from './fares-screen.component';

describe('FaresScreenComponent', () => {
  let component: FaresScreenComponent;
  let fixture: ComponentFixture<FaresScreenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FaresScreenComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FaresScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
