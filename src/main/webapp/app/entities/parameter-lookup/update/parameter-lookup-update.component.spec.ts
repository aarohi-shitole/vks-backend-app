import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ParameterLookupFormService } from './parameter-lookup-form.service';
import { ParameterLookupService } from '../service/parameter-lookup.service';
import { IParameterLookup } from '../parameter-lookup.model';

import { ParameterLookupUpdateComponent } from './parameter-lookup-update.component';

describe('ParameterLookup Management Update Component', () => {
  let comp: ParameterLookupUpdateComponent;
  let fixture: ComponentFixture<ParameterLookupUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let parameterLookupFormService: ParameterLookupFormService;
  let parameterLookupService: ParameterLookupService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ParameterLookupUpdateComponent],
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
      .overrideTemplate(ParameterLookupUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ParameterLookupUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    parameterLookupFormService = TestBed.inject(ParameterLookupFormService);
    parameterLookupService = TestBed.inject(ParameterLookupService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const parameterLookup: IParameterLookup = { id: 456 };

      activatedRoute.data = of({ parameterLookup });
      comp.ngOnInit();

      expect(comp.parameterLookup).toEqual(parameterLookup);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IParameterLookup>>();
      const parameterLookup = { id: 123 };
      jest.spyOn(parameterLookupFormService, 'getParameterLookup').mockReturnValue(parameterLookup);
      jest.spyOn(parameterLookupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ parameterLookup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: parameterLookup }));
      saveSubject.complete();

      // THEN
      expect(parameterLookupFormService.getParameterLookup).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(parameterLookupService.update).toHaveBeenCalledWith(expect.objectContaining(parameterLookup));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IParameterLookup>>();
      const parameterLookup = { id: 123 };
      jest.spyOn(parameterLookupFormService, 'getParameterLookup').mockReturnValue({ id: null });
      jest.spyOn(parameterLookupService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ parameterLookup: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: parameterLookup }));
      saveSubject.complete();

      // THEN
      expect(parameterLookupFormService.getParameterLookup).toHaveBeenCalled();
      expect(parameterLookupService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IParameterLookup>>();
      const parameterLookup = { id: 123 };
      jest.spyOn(parameterLookupService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ parameterLookup });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(parameterLookupService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
