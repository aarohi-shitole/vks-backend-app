import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../member.test-samples';

import { MemberFormService } from './member-form.service';

describe('Member Form Service', () => {
  let service: MemberFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MemberFormService);
  });

  describe('Service methods', () => {
    describe('createMemberFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMemberFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            firstName: expect.any(Object),
            middleName: expect.any(Object),
            lastName: expect.any(Object),
            memberUniqueId: expect.any(Object),
            fatherName: expect.any(Object),
            motherName: expect.any(Object),
            gender: expect.any(Object),
            dob: expect.any(Object),
            email: expect.any(Object),
            mobileNo: expect.any(Object),
            religion: expect.any(Object),
            category: expect.any(Object),
            cast: expect.any(Object),
            aadharCard: expect.any(Object),
            panCard: expect.any(Object),
            rationCard: expect.any(Object),
            familyMemberCount: expect.any(Object),
            occupation: expect.any(Object),
            applicationDate: expect.any(Object),
            status: expect.any(Object),
            kmpStatus: expect.any(Object),
            boardResolutionNo: expect.any(Object),
            boardResolutionDate: expect.any(Object),
            loanStatus: expect.any(Object),
            memberType: expect.any(Object),
            isactive: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            memberBank: expect.any(Object),
            society: expect.any(Object),
          })
        );
      });

      it('passing IMember should create a new form with FormGroup', () => {
        const formGroup = service.createMemberFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            firstName: expect.any(Object),
            middleName: expect.any(Object),
            lastName: expect.any(Object),
            memberUniqueId: expect.any(Object),
            fatherName: expect.any(Object),
            motherName: expect.any(Object),
            gender: expect.any(Object),
            dob: expect.any(Object),
            email: expect.any(Object),
            mobileNo: expect.any(Object),
            religion: expect.any(Object),
            category: expect.any(Object),
            cast: expect.any(Object),
            aadharCard: expect.any(Object),
            panCard: expect.any(Object),
            rationCard: expect.any(Object),
            familyMemberCount: expect.any(Object),
            occupation: expect.any(Object),
            applicationDate: expect.any(Object),
            status: expect.any(Object),
            kmpStatus: expect.any(Object),
            boardResolutionNo: expect.any(Object),
            boardResolutionDate: expect.any(Object),
            loanStatus: expect.any(Object),
            memberType: expect.any(Object),
            isactive: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            memberBank: expect.any(Object),
            society: expect.any(Object),
          })
        );
      });
    });

    describe('getMember', () => {
      it('should return NewMember for default Member initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createMemberFormGroup(sampleWithNewData);

        const member = service.getMember(formGroup) as any;

        expect(member).toMatchObject(sampleWithNewData);
      });

      it('should return NewMember for empty Member initial value', () => {
        const formGroup = service.createMemberFormGroup();

        const member = service.getMember(formGroup) as any;

        expect(member).toMatchObject({});
      });

      it('should return IMember', () => {
        const formGroup = service.createMemberFormGroup(sampleWithRequiredData);

        const member = service.getMember(formGroup) as any;

        expect(member).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMember should not enable id FormControl', () => {
        const formGroup = service.createMemberFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMember should disable id FormControl', () => {
        const formGroup = service.createMemberFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
