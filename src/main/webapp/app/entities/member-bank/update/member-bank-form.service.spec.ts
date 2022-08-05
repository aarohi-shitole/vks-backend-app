import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../member-bank.test-samples';

import { MemberBankFormService } from './member-bank-form.service';

describe('MemberBank Form Service', () => {
  let service: MemberBankFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MemberBankFormService);
  });

  describe('Service methods', () => {
    describe('createMemberBankFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMemberBankFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            bankName: expect.any(Object),
            branchName: expect.any(Object),
            accountNumber: expect.any(Object),
            ifsccode: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            isDeleted: expect.any(Object),
          })
        );
      });

      it('passing IMemberBank should create a new form with FormGroup', () => {
        const formGroup = service.createMemberBankFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            bankName: expect.any(Object),
            branchName: expect.any(Object),
            accountNumber: expect.any(Object),
            ifsccode: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            isDeleted: expect.any(Object),
          })
        );
      });
    });

    describe('getMemberBank', () => {
      it('should return NewMemberBank for default MemberBank initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createMemberBankFormGroup(sampleWithNewData);

        const memberBank = service.getMemberBank(formGroup) as any;

        expect(memberBank).toMatchObject(sampleWithNewData);
      });

      it('should return NewMemberBank for empty MemberBank initial value', () => {
        const formGroup = service.createMemberBankFormGroup();

        const memberBank = service.getMemberBank(formGroup) as any;

        expect(memberBank).toMatchObject({});
      });

      it('should return IMemberBank', () => {
        const formGroup = service.createMemberBankFormGroup(sampleWithRequiredData);

        const memberBank = service.getMemberBank(formGroup) as any;

        expect(memberBank).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMemberBank should not enable id FormControl', () => {
        const formGroup = service.createMemberBankFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMemberBank should disable id FormControl', () => {
        const formGroup = service.createMemberBankFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
