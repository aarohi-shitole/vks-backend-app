import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../parameter-lookup.test-samples';

import { ParameterLookupFormService } from './parameter-lookup-form.service';

describe('ParameterLookup Form Service', () => {
  let service: ParameterLookupFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ParameterLookupFormService);
  });

  describe('Service methods', () => {
    describe('createParameterLookupFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createParameterLookupFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            parameterType: expect.any(Object),
            description: expect.any(Object),
            displayValue: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            isDeleted: expect.any(Object),
          })
        );
      });

      it('passing IParameterLookup should create a new form with FormGroup', () => {
        const formGroup = service.createParameterLookupFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            parameterType: expect.any(Object),
            description: expect.any(Object),
            displayValue: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            isDeleted: expect.any(Object),
          })
        );
      });
    });

    describe('getParameterLookup', () => {
      it('should return NewParameterLookup for default ParameterLookup initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createParameterLookupFormGroup(sampleWithNewData);

        const parameterLookup = service.getParameterLookup(formGroup) as any;

        expect(parameterLookup).toMatchObject(sampleWithNewData);
      });

      it('should return NewParameterLookup for empty ParameterLookup initial value', () => {
        const formGroup = service.createParameterLookupFormGroup();

        const parameterLookup = service.getParameterLookup(formGroup) as any;

        expect(parameterLookup).toMatchObject({});
      });

      it('should return IParameterLookup', () => {
        const formGroup = service.createParameterLookupFormGroup(sampleWithRequiredData);

        const parameterLookup = service.getParameterLookup(formGroup) as any;

        expect(parameterLookup).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IParameterLookup should not enable id FormControl', () => {
        const formGroup = service.createParameterLookupFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewParameterLookup should disable id FormControl', () => {
        const formGroup = service.createParameterLookupFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
