import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MemberBankDetailComponent } from './member-bank-detail.component';

describe('MemberBank Management Detail Component', () => {
  let comp: MemberBankDetailComponent;
  let fixture: ComponentFixture<MemberBankDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MemberBankDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ memberBank: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MemberBankDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MemberBankDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load memberBank on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.memberBank).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
