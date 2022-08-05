import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../society.test-samples';

import { SocietyFormService } from './society-form.service';

describe('Society Form Service', () => {
  let service: SocietyFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SocietyFormService);
  });

  describe('Service methods', () => {
    describe('createSocietyFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSocietyFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            societyName: expect.any(Object),
            address: expect.any(Object),
            village: expect.any(Object),
            tahsil: expect.any(Object),
            state: expect.any(Object),
            district: expect.any(Object),
            logo: expect.any(Object),
            registrationNumber: expect.any(Object),
            gstinNumber: expect.any(Object),
            panNumber: expect.any(Object),
            tanNumber: expect.any(Object),
            phoneNumber: expect.any(Object),
            emailAddress: expect.any(Object),
            pinCode: expect.any(Object),
            createdOn: expect.any(Object),
            createdBy: expect.any(Object),
            description: expect.any(Object),
            isActivate: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            society: expect.any(Object),
          })
        );
      });

      it('passing ISociety should create a new form with FormGroup', () => {
        const formGroup = service.createSocietyFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            societyName: expect.any(Object),
            address: expect.any(Object),
            village: expect.any(Object),
            tahsil: expect.any(Object),
            state: expect.any(Object),
            district: expect.any(Object),
            logo: expect.any(Object),
            registrationNumber: expect.any(Object),
            gstinNumber: expect.any(Object),
            panNumber: expect.any(Object),
            tanNumber: expect.any(Object),
            phoneNumber: expect.any(Object),
            emailAddress: expect.any(Object),
            pinCode: expect.any(Object),
            createdOn: expect.any(Object),
            createdBy: expect.any(Object),
            description: expect.any(Object),
            isActivate: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            society: expect.any(Object),
          })
        );
      });
    });

    describe('getSociety', () => {
      it('should return NewSociety for default Society initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createSocietyFormGroup(sampleWithNewData);

        const society = service.getSociety(formGroup) as any;

        expect(society).toMatchObject(sampleWithNewData);
      });

      it('should return NewSociety for empty Society initial value', () => {
        const formGroup = service.createSocietyFormGroup();

        const society = service.getSociety(formGroup) as any;

        expect(society).toMatchObject({});
      });

      it('should return ISociety', () => {
        const formGroup = service.createSocietyFormGroup(sampleWithRequiredData);

        const society = service.getSociety(formGroup) as any;

        expect(society).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISociety should not enable id FormControl', () => {
        const formGroup = service.createSocietyFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSociety should disable id FormControl', () => {
        const formGroup = service.createSocietyFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
