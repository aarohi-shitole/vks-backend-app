import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { MemberLandAssetsFormService } from './member-land-assets-form.service';
import { MemberLandAssetsService } from '../service/member-land-assets.service';
import { IMemberLandAssets } from '../member-land-assets.model';

import { MemberLandAssetsUpdateComponent } from './member-land-assets-update.component';

describe('MemberLandAssets Management Update Component', () => {
  let comp: MemberLandAssetsUpdateComponent;
  let fixture: ComponentFixture<MemberLandAssetsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let memberLandAssetsFormService: MemberLandAssetsFormService;
  let memberLandAssetsService: MemberLandAssetsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [MemberLandAssetsUpdateComponent],
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
      .overrideTemplate(MemberLandAssetsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MemberLandAssetsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    memberLandAssetsFormService = TestBed.inject(MemberLandAssetsFormService);
    memberLandAssetsService = TestBed.inject(MemberLandAssetsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const memberLandAssets: IMemberLandAssets = { id: 456 };

      activatedRoute.data = of({ memberLandAssets });
      comp.ngOnInit();

      expect(comp.memberLandAssets).toEqual(memberLandAssets);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMemberLandAssets>>();
      const memberLandAssets = { id: 123 };
      jest.spyOn(memberLandAssetsFormService, 'getMemberLandAssets').mockReturnValue(memberLandAssets);
      jest.spyOn(memberLandAssetsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ memberLandAssets });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: memberLandAssets }));
      saveSubject.complete();

      // THEN
      expect(memberLandAssetsFormService.getMemberLandAssets).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(memberLandAssetsService.update).toHaveBeenCalledWith(expect.objectContaining(memberLandAssets));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMemberLandAssets>>();
      const memberLandAssets = { id: 123 };
      jest.spyOn(memberLandAssetsFormService, 'getMemberLandAssets').mockReturnValue({ id: null });
      jest.spyOn(memberLandAssetsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ memberLandAssets: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: memberLandAssets }));
      saveSubject.complete();

      // THEN
      expect(memberLandAssetsFormService.getMemberLandAssets).toHaveBeenCalled();
      expect(memberLandAssetsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMemberLandAssets>>();
      const memberLandAssets = { id: 123 };
      jest.spyOn(memberLandAssetsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ memberLandAssets });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(memberLandAssetsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
