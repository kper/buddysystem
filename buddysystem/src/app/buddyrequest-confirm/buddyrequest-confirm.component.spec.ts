import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BuddyrequestConfirmComponent } from './buddyrequest-confirm.component';

describe('BuddyrequestConfirmComponent', () => {
  let component: BuddyrequestConfirmComponent;
  let fixture: ComponentFixture<BuddyrequestConfirmComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BuddyrequestConfirmComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BuddyrequestConfirmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
