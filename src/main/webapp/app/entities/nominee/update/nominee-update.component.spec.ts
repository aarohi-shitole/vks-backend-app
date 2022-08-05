import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NomineeFormService } from './nominee-form.service';
import { NomineeService } from '../service/nominee.service';
import { INominee } from '../nominee.model';
import { IMember } from 'app/entities/member/member.model';
import { MemberService } from 'app/entities/member/service/member.service';

import { NomineeUpdateComponent } from './nominee-update.component';

describe('Nominee Management Update Component', () => {
  let comp: NomineeUpdateComponent;
  let fixture: ComponentFixture<NomineeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let nomineeFormService: NomineeFormService;
  let nomineeService: NomineeService;
  let memberService: MemberService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [NomineeUpdateComponent],
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
      .overrideTemplate(NomineeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NomineeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    nomineeFormService = TestBed.inject(NomineeFormService);
    nomineeService = TestBed.inject(NomineeService);
    memberService = TestBed.inject(MemberService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Member query and add missing value', () => {
      const nominee: INominee = { id: 456 };
      const member: IMember = { id: 63311 };
      nominee.member = member;

      const memberCollection: IMember[] = [{ id: 92119 }];
      jest.spyOn(memberService, 'query').mockReturnValue(of(new HttpResponse({ body: memberCollection })));
      const additionalMembers = [member];
      const expectedCollection: IMember[] = [...additionalMembers, ...memberCollection];
      jest.spyOn(memberService, 'addMemberToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ nominee });
      comp.ngOnInit();

      expect(memberService.query).toHaveBeenCalled();
      expect(memberService.addMemberToCollectionIfMissing).toHaveBeenCalledWith(
        memberCollection,
        ...additionalMembers.map(expect.objectContaining)
      );
      expect(comp.membersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const nominee: INominee = { id: 456 };
      const member: IMember = { id: 26756 };
      nominee.member = member;

      activatedRoute.data = of({ nominee });
      comp.ngOnInit();

      expect(comp.membersSharedCollection).toContain(member);
      expect(comp.nominee).toEqual(nominee);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INominee>>();
      const nominee = { id: 123 };
      jest.spyOn(nomineeFormService, 'getNominee').mockReturnValue(nominee);
      jest.spyOn(nomineeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nominee });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nominee }));
      saveSubject.complete();

      // THEN
      expect(nomineeFormService.getNominee).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(nomineeService.update).toHaveBeenCalledWith(expect.objectContaining(nominee));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INominee>>();
      const nominee = { id: 123 };
      jest.spyOn(nomineeFormService, 'getNominee').mockReturnValue({ id: null });
      jest.spyOn(nomineeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nominee: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nominee }));
      saveSubject.complete();

      // THEN
      expect(nomineeFormService.getNominee).toHaveBeenCalled();
      expect(nomineeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INominee>>();
      const nominee = { id: 123 };
      jest.spyOn(nomineeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nominee });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(nomineeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareMember', () => {
      it('Should forward to memberService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(memberService, 'compareMember');
        comp.compareMember(entity, entity2);
        expect(memberService.compareMember).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
