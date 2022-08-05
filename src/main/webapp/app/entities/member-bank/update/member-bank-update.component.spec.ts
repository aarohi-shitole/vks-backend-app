import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MemberBankFormService } from './member-bank-form.service';
import { MemberBankService } from '../service/member-bank.service';
import { IMemberBank } from '../member-bank.model';

import { MemberBankUpdateComponent } from './member-bank-update.component';

describe('MemberBank Management Update Component', () => {
  let comp: MemberBankUpdateComponent;
  let fixture: ComponentFixture<MemberBankUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let memberBankFormService: MemberBankFormService;
  let memberBankService: MemberBankService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MemberBankUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(MemberBankUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MemberBankUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    memberBankFormService = TestBed.inject(MemberBankFormService);
    memberBankService = TestBed.inject(MemberBankService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const memberBank: IMemberBank = { id: 456 };

      activatedRoute.data = of({ memberBank });
      comp.ngOnInit();

      expect(comp.memberBank).toEqual(memberBank);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMemberBank>>();
      const memberBank = { id: 123 };
      jest.spyOn(memberBankFormService, 'getMemberBank').mockReturnValue(memberBank);
      jest.spyOn(memberBankService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ memberBank });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: memberBank }));
      saveSubject.complete();

      // THEN
      expect(memberBankFormService.getMemberBank).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(memberBankService.update).toHaveBeenCalledWith(expect.objectContaining(memberBank));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMemberBank>>();
      const memberBank = { id: 123 };
      jest.spyOn(memberBankFormService, 'getMemberBank').mockReturnValue({ id: null });
      jest.spyOn(memberBankService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ memberBank: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: memberBank }));
      saveSubject.complete();

      // THEN
      expect(memberBankFormService.getMemberBank).toHaveBeenCalled();
      expect(memberBankService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMemberBank>>();
      const memberBank = { id: 123 };
      jest.spyOn(memberBankService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ memberBank });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(memberBankService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
