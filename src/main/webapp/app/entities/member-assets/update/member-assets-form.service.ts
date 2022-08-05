import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IMemberAssets, NewMemberAssets } from '../member-assets.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMemberAssets for edit and NewMemberAssetsFormGroupInput for create.
 */
type MemberAssetsFormGroupInput = IMemberAssets | PartialWithRequiredKeyOf<NewMemberAssets>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IMemberAssets | NewMemberAssets> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type MemberAssetsFormRawValue = FormValueOf<IMemberAssets>;

type NewMemberAssetsFormRawValue = FormValueOf<NewMemberAssets>;

type MemberAssetsFormDefaults = Pick<NewMemberAssets, 'id' | 'lastModified' | 'createdOn' | 'isDeleted'>;

type MemberAssetsFormGroupContent = {
  id: FormControl<MemberAssetsFormRawValue['id'] | NewMemberAssets['id']>;
  assetAmount: FormControl<MemberAssetsFormRawValue['assetAmount']>;
  otherDocument1: FormControl<MemberAssetsFormRawValue['otherDocument1']>;
  otherDocument1ContentType: FormControl<MemberAssetsFormRawValue['otherDocument1ContentType']>;
  otherDocument2: FormControl<MemberAssetsFormRawValue['otherDocument2']>;
  otherDocument2ContentType: FormControl<MemberAssetsFormRawValue['otherDocument2ContentType']>;
  assetType: FormControl<MemberAssetsFormRawValue['assetType']>;
  assetArea: FormControl<MemberAssetsFormRawValue['assetArea']>;
  assetAddress: FormControl<MemberAssetsFormRawValue['assetAddress']>;
  numberOfAssets: FormControl<MemberAssetsFormRawValue['numberOfAssets']>;
  lastModified: FormControl<MemberAssetsFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<MemberAssetsFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<MemberAssetsFormRawValue['createdBy']>;
  createdOn: FormControl<MemberAssetsFormRawValue['createdOn']>;
  isDeleted: FormControl<MemberAssetsFormRawValue['isDeleted']>;
  member: FormControl<MemberAssetsFormRawValue['member']>;
};

export type MemberAssetsFormGroup = FormGroup<MemberAssetsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MemberAssetsFormService {
  createMemberAssetsFormGroup(memberAssets: MemberAssetsFormGroupInput = { id: null }): MemberAssetsFormGroup {
    const memberAssetsRawValue = this.convertMemberAssetsToMemberAssetsRawValue({
      ...this.getFormDefaults(),
      ...memberAssets,
    });
    return new FormGroup<MemberAssetsFormGroupContent>({
      id: new FormControl(
        { value: memberAssetsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      assetAmount: new FormControl(memberAssetsRawValue.assetAmount),
      otherDocument1: new FormControl(memberAssetsRawValue.otherDocument1),
      otherDocument1ContentType: new FormControl(memberAssetsRawValue.otherDocument1ContentType),
      otherDocument2: new FormControl(memberAssetsRawValue.otherDocument2),
      otherDocument2ContentType: new FormControl(memberAssetsRawValue.otherDocument2ContentType),
      assetType: new FormControl(memberAssetsRawValue.assetType),
      assetArea: new FormControl(memberAssetsRawValue.assetArea),
      assetAddress: new FormControl(memberAssetsRawValue.assetAddress),
      numberOfAssets: new FormControl(memberAssetsRawValue.numberOfAssets),
      lastModified: new FormControl(memberAssetsRawValue.lastModified),
      lastModifiedBy: new FormControl(memberAssetsRawValue.lastModifiedBy),
      createdBy: new FormControl(memberAssetsRawValue.createdBy),
      createdOn: new FormControl(memberAssetsRawValue.createdOn),
      isDeleted: new FormControl(memberAssetsRawValue.isDeleted),
      member: new FormControl(memberAssetsRawValue.member),
    });
  }

  getMemberAssets(form: MemberAssetsFormGroup): IMemberAssets | NewMemberAssets {
    return this.convertMemberAssetsRawValueToMemberAssets(form.getRawValue() as MemberAssetsFormRawValue | NewMemberAssetsFormRawValue);
  }

  resetForm(form: MemberAssetsFormGroup, memberAssets: MemberAssetsFormGroupInput): void {
    const memberAssetsRawValue = this.convertMemberAssetsToMemberAssetsRawValue({ ...this.getFormDefaults(), ...memberAssets });
    form.reset(
      {
        ...memberAssetsRawValue,
        id: { value: memberAssetsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MemberAssetsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastModified: currentTime,
      createdOn: currentTime,
      isDeleted: false,
    };
  }

  private convertMemberAssetsRawValueToMemberAssets(
    rawMemberAssets: MemberAssetsFormRawValue | NewMemberAssetsFormRawValue
  ): IMemberAssets | NewMemberAssets {
    return {
      ...rawMemberAssets,
      lastModified: dayjs(rawMemberAssets.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawMemberAssets.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertMemberAssetsToMemberAssetsRawValue(
    memberAssets: IMemberAssets | (Partial<NewMemberAssets> & MemberAssetsFormDefaults)
  ): MemberAssetsFormRawValue | PartialWithRequiredKeyOf<NewMemberAssetsFormRawValue> {
    return {
      ...memberAssets,
      lastModified: memberAssets.lastModified ? memberAssets.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: memberAssets.createdOn ? memberAssets.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
