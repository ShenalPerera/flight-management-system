import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FareItemComponent } from './fare-item.component';

describe('EntryComponent', () => {
  let component: FareItemComponent;
  let fixture: ComponentFixture<FareItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FareItemComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FareItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
