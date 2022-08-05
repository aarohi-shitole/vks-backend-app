import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IMemberLandAssets, NewMemberLandAssets } from '../member-land-assets.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMemberLandAssets for edit and NewMemberLandAssetsFormGroupInput for create.
 */
type MemberLandAssetsFormGroupInput = IMemberLandAssets | PartialWithRequiredKeyOf<NewMemberLandAssets>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IMemberLandAssets | NewMemberLandAssets> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type MemberLandAssetsFormRawValue = FormValueOf<IMemberLandAssets>;

type NewMemberLandAssetsFormRawValue = FormValueOf<NewMemberLandAssets>;

type MemberLandAssetsFormDefaults = Pick<NewMemberLandAssets, 'id' | 'assigneeOfLand' | 'isDeleted' | 'lastModified' | 'createdOn'>;

type MemberLandAssetsFormGroupContent = {
  id: FormControl<MemberLandAssetsFormRawValue['id'] | NewMemberLandAssets['id']>;
  landType: FormControl<MemberLandAssetsFormRawValue['landType']>;
  landGatNo: FormControl<MemberLandAssetsFormRawValue['landGatNo']>;
  landAreaInHector: FormControl<MemberLandAssetsFormRawValue['landAreaInHector']>;
  jindagiPatrakNo: FormControl<MemberLandAssetsFormRawValue['jindagiPatrakNo']>;
  jindagiAmount: FormControl<MemberLandAssetsFormRawValue['jindagiAmount']>;
  assetLandAddress: FormControl<MemberLandAssetsFormRawValue['assetLandAddress']>;
  valueOfLand: FormControl<MemberLandAssetsFormRawValue['valueOfLand']>;
  assigneeOfLand: FormControl<MemberLandAssetsFormRawValue['assigneeOfLand']>;
  isDeleted: FormControl<MemberLandAssetsFormRawValue['isDeleted']>;
  mLLoanNo: FormControl<MemberLandAssetsFormRawValue['mLLoanNo']>;
  jindagiPatrak: FormControl<MemberLandAssetsFormRawValue['jindagiPatrak']>;
  jindagiPatrakContentType: FormControl<MemberLandAssetsFormRawValue['jindagiPatrakContentType']>;
  eightA: FormControl<MemberLandAssetsFormRawValue['eightA']>;
  eightAContentType: FormControl<MemberLandAssetsFormRawValue['eightAContentType']>;
  saatBara: FormControl<MemberLandAssetsFormRawValue['saatBara']>;
  saatBaraContentType: FormControl<MemberLandAssetsFormRawValue['saatBaraContentType']>;
  lastModified: FormControl<MemberLandAssetsFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<MemberLandAssetsFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<MemberLandAssetsFormRawValue['createdBy']>;
  createdOn: FormControl<MemberLandAssetsFormRawValue['createdOn']>;
};

export type MemberLandAssetsFormGroup = FormGroup<MemberLandAssetsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MemberLandAssetsFormService {
  createMemberLandAssetsFormGroup(memberLandAssets: MemberLandAssetsFormGroupInput = { id: null }): MemberLandAssetsFormGroup {
    const memberLandAssetsRawValue = this.convertMemberLandAssetsToMemberLandAssetsRawValue({
      ...this.getFormDefaults(),
      ...memberLandAssets,
    });
    return new FormGroup<MemberLandAssetsFormGroupContent>({
      id: new FormControl(
        { value: memberLandAssetsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      landType: new FormControl(memberLandAssetsRawValue.landType),
      landGatNo: new FormControl(memberLandAssetsRawValue.landGatNo),
      landAreaInHector: new FormControl(memberLandAssetsRawValue.landAreaInHector),
      jindagiPatrakNo: new FormControl(memberLandAssetsRawValue.jindagiPatrakNo),
      jindagiAmount: new FormControl(memberLandAssetsRawValue.jindagiAmount),
      assetLandAddress: new FormControl(memberLandAssetsRawValue.assetLandAddress),
      valueOfLand: new FormControl(memberLandAssetsRawValue.valueOfLand),
      assigneeOfLand: new FormControl(memberLandAssetsRawValue.assigneeOfLand),
      isDeleted: new FormControl(memberLandAssetsRawValue.isDeleted),
      mLLoanNo: new FormControl(memberLandAssetsRawValue.mLLoanNo),
      jindagiPatrak: new FormControl(memberLandAssetsRawValue.jindagiPatrak),
      jindagiPatrakContentType: new FormControl(memberLandAssetsRawValue.jindagiPatrakContentType),
      eightA: new FormControl(memberLandAssetsRawValue.eightA),
      eightAContentType: new FormControl(memberLandAssetsRawValue.eightAContentType),
      saatBara: new FormControl(memberLandAssetsRawValue.saatBara),
      saatBaraContentType: new FormControl(memberLandAssetsRawValue.saatBaraContentType),
      lastModified: new FormControl(memberLandAssetsRawValue.lastModified),
      lastModifiedBy: new FormControl(memberLandAssetsRawValue.lastModifiedBy),
      createdBy: new FormControl(memberLandAssetsRawValue.createdBy),
      createdOn: new FormControl(memberLandAssetsRawValue.createdOn),
    });
  }

  getMemberLandAssets(form: MemberLandAssetsFormGroup): IMemberLandAssets | NewMemberLandAssets {
    return this.convertMemberLandAssetsRawValueToMemberLandAssets(
      form.getRawValue() as MemberLandAssetsFormRawValue | NewMemberLandAssetsFormRawValue
    );
  }

  resetForm(form: MemberLandAssetsFormGroup, memberLandAssets: MemberLandAssetsFormGroupInput): void {
    const memberLandAssetsRawValue = this.convertMemberLandAssetsToMemberLandAssetsRawValue({
      ...this.getFormDefaults(),
      ...memberLandAssets,
    });
    form.reset(
      {
        ...memberLandAssetsRawValue,
        id: { value: memberLandAssetsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MemberLandAssetsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      assigneeOfLand: false,
      isDeleted: false,
      lastModified: currentTime,
      createdOn: currentTime,
    };
  }

  private convertMemberLandAssetsRawValueToMemberLandAssets(
    rawMemberLandAssets: MemberLandAssetsFormRawValue | NewMemberLandAssetsFormRawValue
  ): IMemberLandAssets | NewMemberLandAssets {
    return {
      ...rawMemberLandAssets,
      lastModified: dayjs(rawMemberLandAssets.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawMemberLandAssets.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertMemberLandAssetsToMemberLandAssetsRawValue(
    memberLandAssets: IMemberLandAssets | (Partial<NewMemberLandAssets> & MemberLandAssetsFormDefaults)
  ): MemberLandAssetsFormRawValue | PartialWithRequiredKeyOf<NewMemberLandAssetsFormRawValue> {
    return {
      ...memberLandAssets,
      lastModified: memberLandAssets.lastModified ? memberLandAssets.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: memberLandAssets.createdOn ? memberLandAssets.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
