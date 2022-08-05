import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../member-land-assets.test-samples';

import { MemberLandAssetsFormService } from './member-land-assets-form.service';

describe('MemberLandAssets Form Service', () => {
  let service: MemberLandAssetsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MemberLandAssetsFormService);
  });

  describe('Service methods', () => {
    describe('createMemberLandAssetsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMemberLandAssetsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            landType: expect.any(Object),
            landGatNo: expect.any(Object),
            landAreaInHector: expect.any(Object),
            jindagiPatrakNo: expect.any(Object),
            jindagiAmount: expect.any(Object),
            assetLandAddress: expect.any(Object),
            valueOfLand: expect.any(Object),
            assigneeOfLand: expect.any(Object),
            isDeleted: expect.any(Object),
            mLLoanNo: expect.any(Object),
            jindagiPatrak: expect.any(Object),
            eightA: expect.any(Object),
            saatBara: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
          })
        );
      });

      it('passing IMemberLandAssets should create a new form with FormGroup', () => {
        const formGroup = service.createMemberLandAssetsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            landType: expect.any(Object),
            landGatNo: expect.any(Object),
            landAreaInHector: expect.any(Object),
            jindagiPatrakNo: expect.any(Object),
            jindagiAmount: expect.any(Object),
            assetLandAddress: expect.any(Object),
            valueOfLand: expect.any(Object),
            assigneeOfLand: expect.any(Object),
            isDeleted: expect.any(Object),
            mLLoanNo: expect.any(Object),
            jindagiPatrak: expect.any(Object),
            eightA: expect.any(Object),
            saatBara: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
          })
        );
      });
    });

    describe('getMemberLandAssets', () => {
      it('should return NewMemberLandAssets for default MemberLandAssets initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createMemberLandAssetsFormGroup(sampleWithNewData);

        const memberLandAssets = service.getMemberLandAssets(formGroup) as any;

        expect(memberLandAssets).toMatchObject(sampleWithNewData);
      });

      it('should return NewMemberLandAssets for empty MemberLandAssets initial value', () => {
        const formGroup = service.createMemberLandAssetsFormGroup();

        const memberLandAssets = service.getMemberLandAssets(formGroup) as any;

        expect(memberLandAssets).toMatchObject({});
      });

      it('should return IMemberLandAssets', () => {
        const formGroup = service.createMemberLandAssetsFormGroup(sampleWithRequiredData);

        const memberLandAssets = service.getMemberLandAssets(formGroup) as any;

        expect(memberLandAssets).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMemberLandAssets should not enable id FormControl', () => {
        const formGroup = service.createMemberLandAssetsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMemberLandAssets should disable id FormControl', () => {
        const formGroup = service.createMemberLandAssetsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
