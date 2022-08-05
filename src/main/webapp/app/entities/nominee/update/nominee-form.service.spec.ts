import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../nominee.test-samples';

import { NomineeFormService } from './nominee-form.service';

describe('Nominee Form Service', () => {
  let service: NomineeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NomineeFormService);
  });

  describe('Service methods', () => {
    describe('createNomineeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createNomineeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            firstName: expect.any(Object),
            middleName: expect.any(Object),
            lastName: expect.any(Object),
            fatherName: expect.any(Object),
            motherName: expect.any(Object),
            guardianName: expect.any(Object),
            gender: expect.any(Object),
            dob: expect.any(Object),
            aadharCard: expect.any(Object),
            nomineeDeclareDate: expect.any(Object),
            relation: expect.any(Object),
            permanentAddr: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            isDeleted: expect.any(Object),
            member: expect.any(Object),
          })
        );
      });

      it('passing INominee should create a new form with FormGroup', () => {
        const formGroup = service.createNomineeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            firstName: expect.any(Object),
            middleName: expect.any(Object),
            lastName: expect.any(Object),
            fatherName: expect.any(Object),
            motherName: expect.any(Object),
            guardianName: expect.any(Object),
            gender: expect.any(Object),
            dob: expect.any(Object),
            aadharCard: expect.any(Object),
            nomineeDeclareDate: expect.any(Object),
            relation: expect.any(Object),
            permanentAddr: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            isDeleted: expect.any(Object),
            member: expect.any(Object),
          })
        );
      });
    });

    describe('getNominee', () => {
      it('should return NewNominee for default Nominee initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createNomineeFormGroup(sampleWithNewData);

        const nominee = service.getNominee(formGroup) as any;

        expect(nominee).toMatchObject(sampleWithNewData);
      });

      it('should return NewNominee for empty Nominee initial value', () => {
        const formGroup = service.createNomineeFormGroup();

        const nominee = service.getNominee(formGroup) as any;

        expect(nominee).toMatchObject({});
      });

      it('should return INominee', () => {
        const formGroup = service.createNomineeFormGroup(sampleWithRequiredData);

        const nominee = service.getNominee(formGroup) as any;

        expect(nominee).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing INominee should not enable id FormControl', () => {
        const formGroup = service.createNomineeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewNominee should disable id FormControl', () => {
        const formGroup = service.createNomineeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
