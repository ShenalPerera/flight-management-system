import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoutesScreenComponent } from './routes-screen.component';

describe('RoutesScreenComponent', () => {
  let component: RoutesScreenComponent;
  let fixture: ComponentFixture<RoutesScreenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RoutesScreenComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RoutesScreenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
